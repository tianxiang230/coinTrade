package com.tx.coin.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.config.PropertyConfig;
import com.tx.coin.entity.OrderRecord;
import com.tx.coin.enums.OrderType;
import com.tx.coin.enums.ResponseCode;
import com.tx.coin.enums.TradeType;
import com.tx.coin.repository.OrderRecordRepository;
import com.tx.coin.service.ICoinTradeService;
import com.tx.coin.utils.EncryptHelper;
import com.tx.coin.utils.HttpUtil;
import com.tx.coin.utils.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
    private DecimalFormat decimalFormat = new DecimalFormat("####.########");

    Logger logger = LoggerFactory.getLogger(CoinQuotationsServiceImpl.class);

    @Override
    public String coinTrade(String symbol, TradeType tradeType, double price, double amount) {
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        logger.info(String.format("执行交易[%s]操作,交易币种[%s],价格[%f],交易量[%f]", tradeType.getName(), symbol, price, amount));
        if (StringUtils.isBlank(symbol) || price <= 0 || amount <= 0) {
            logger.info("交易参数不合法,symbol:{},price:{},amount:{}", new Object[]{symbol, price, amount});
            return null;
        }
        if (price == 0 || amount == 0) {
            logger.info("交易价格或交易量为0,禁止交易");
            return null;
        }
        price = new BigDecimal(price).setScale(8, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        String orderId = null;
        String secretKey = propertyConfig.getSecretKey();
        Map<String, String> param = new HashMap<>(10);
        param.put("api_key", propertyConfig.getApiKey());
        param.put("symbol", symbol);
        param.put("type", tradeType.getCode());
        param.put("price", decimalFormat.format(price));
        param.put("amount", decimalFormat.format(amount));
        String sign = EncryptHelper.sign(param, secretKey, "utf-8");
        param.put("sign", sign);
        param.put("secret_key", secretKey);
        String result = HttpUtil.doPostSSL(tradeUrl, param);
        logger.info("币币交易请求参数:{},响应:{}", JsonMapper.nonDefaultMapper().toJson(param), result);
        try {
            boolean state = false;
            JsonNode resultNode = mapper.readTree(result).get("result");
            if (resultNode == null) {
                Integer errorCode = mapper.readTree(result).get("error_code").asInt();
                String errorMsg = ResponseCode.responseCode.get(errorCode);
                logger.info("下单交易出错:{}", errorMsg);
            } else {
                state = resultNode.asBoolean();
            }

            OrderRecord orderRecord = null;
            if (state) {
                orderId = mapper.readTree(result).get("order_id").toString();
                orderRecord = new OrderRecord(symbol, tradeType, OrderType.COMPLETED, price, amount);
                orderRecord.setOrderId(orderId);
            } else {
                orderRecord = new OrderRecord(symbol, tradeType, OrderType.NOT_COMPLETE, price, amount);
            }

            orderRecordRepository.save(orderRecord);
        } catch (IOException e) {
            logger.info("交易发生异常，异常信息:{}", ExceptionUtils.getStackTrace(e));
        }
        return orderId;
    }

    @Override
    public boolean cancelTrade(String symbol, String orderId) {
        if (StringUtils.isBlank(symbol) || StringUtils.isBlank(orderId)) {
            logger.info("取消订单,缺少必要参数,symbol:{},orderId:{}", symbol, orderId);
            return false;
        }
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
        logger.info("取消订单操作,请求参数:{},响应:{}", JsonMapper.nonDefaultMapper().toJson(param), result);
        try {
            JsonNode successNode = mapper.readTree(result).get("success");
            if (successNode == null) {
                JsonNode resultNode = mapper.readTree(result).get("result");
                if (resultNode != null && resultNode.asBoolean()) {
                    return true;
                }
                Integer errorCode = mapper.readTree(result).get("error_code").asInt();
                logger.info(ResponseCode.responseCode.get(errorCode));
                return false;
            }
            String successOrders = mapper.readTree(result).get("success").toString();
            String failOrders = mapper.readTree(result).get("error").toString();
            logger.info("撤销成功订单:{},失败订单:{}", successOrders, failOrders);
            if (StringUtils.isNotBlank(failOrders)) {
                return cancelTrade(symbol, failOrders);
            }
        } catch (IOException e) {
            logger.info("撤单交易发生异常，异常信息:{}", ExceptionUtils.getStackTrace(e));
        }
        return false;
    }
}
