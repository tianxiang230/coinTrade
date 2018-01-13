package com.tx.coin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.config.PropertyConfig;
import com.tx.coin.entity.OrderRecord;
import com.tx.coin.enums.OrderType;
import com.tx.coin.enums.TradeType;
import com.tx.coin.repository.OrderRecordRepository;
import com.tx.coin.service.ICoinTradeService;
import com.tx.coin.utils.EncryptHelper;
import com.tx.coin.utils.HttpUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 你慧快乐 on 2018-1-10.
 */
@Service
public class CoinTradeServiceImpl implements ICoinTradeService {
    @Autowired
    private PropertyConfig propertyConfig;
    @Autowired
    private OrderRecordRepository orderRecordRepository;
    @Value("${coin.remote.trade}")
    private String tradeUrl;
    @Value("${coin.remote.cancelOrder}")
    private String cancelOrderUrl;
    private ObjectMapper mapper = new ObjectMapper();

    Logger logger = LoggerFactory.getLogger(CoinQuotationsServiceImpl.class);

    @Override
    public String coinTrade(String symbol, TradeType tradeType, double price, double amount) {
        String orderId = null;
        String secretKey = propertyConfig.getSecretKey();
        Map<String, String> param = new HashMap<>();
        param.put("api_key", propertyConfig.getApiKey());
        param.put("symbol", symbol);
        param.put("type", tradeType.getCode());
        param.put("price", String.valueOf(price));
        param.put("amount", String.valueOf(amount));
        String sign = EncryptHelper.sign(param, secretKey, "utf-8");
        param.put("sign", sign);
        param.put("secret_key", secretKey);
        String result = HttpUtil.doPostSSL(tradeUrl, param);
        try {
            boolean state = mapper.readTree(result).get("result").asBoolean();
            OrderRecord orderRecord = null;
            if (state) {
                orderId = mapper.readTree(result).get("order_id").toString();
                orderRecord = new OrderRecord(symbol, tradeType, OrderType.COMPLETED, price, amount);
            } else {
                orderRecord = new OrderRecord(symbol, tradeType, OrderType.NOT_COMPLETE, price, amount);
            }
            logger.info("执行交易[{}]操作,交易币种[{}],价格[{}],交易量[{}]", tradeType.getName(), symbol, price, amount);
            orderRecordRepository.save(orderRecord);
        } catch (IOException e) {
            logger.info("交易发生异常，异常信息:{}", ExceptionUtils.getStackTrace(e));
        }
        logger.info("币币交易返回:{}", result);
        return orderId;
    }

    @Override
    public boolean cancelTrade(String symbol, String orderId) {
        String secretKey = propertyConfig.getSecretKey();
        Map<String, String> param = new HashMap<>();
        param.put("api_key", propertyConfig.getApiKey());
        param.put("symbol", symbol);
        param.put("order_id", orderId);
        String sign = EncryptHelper.sign(param, secretKey, "utf-8");
        param.put("sign", sign);
        param.put("secret_key", secretKey);
        String result = null;
        result = HttpUtil.doPostSSL(cancelOrderUrl, param);
        try {
            boolean state = mapper.readTree(result).get("result").asBoolean();
            if (state) {
                logger.info("撤单{}成功", orderId);
                return state;
            }
        } catch (IOException e) {
            logger.info("撤单交易发生异常，异常信息:{}", ExceptionUtils.getStackTrace(e));
        }
        return false;
    }
}
