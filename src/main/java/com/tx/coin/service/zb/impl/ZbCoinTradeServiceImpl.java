package com.tx.coin.service.zb.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.context.PlatConfigContext;
import com.tx.coin.entity.OrderRecord;
import com.tx.coin.entity.PlatFormConfig;
import com.tx.coin.enums.OrderType;
import com.tx.coin.enums.PlatType;
import com.tx.coin.enums.TradeType;
import com.tx.coin.repository.OrderRecordRepository;
import com.tx.coin.repository.PlatFormConfigRepository;
import com.tx.coin.service.ICoinTradeService;
import com.tx.coin.utils.Digests;
import com.tx.coin.utils.HttpUtil;
import com.tx.coin.utils.SortMapUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.zb.impl
 * @Description ZB下单和取消订单
 * @date 2018-2-5 19:41
 */
@Service
public class ZbCoinTradeServiceImpl implements ICoinTradeService {
    @Autowired
    private PlatFormConfigRepository configRepository;
    @Autowired
    private OrderRecordRepository orderRecordRepository;

    private final String tradeUrl = "https://trade.zb.com/api/order";
    private final String tradeMethod = "order";

    private final String cancelUrl = "https://trade.zb.com/api/cancelOrder";
    private final String cancelMethod = "cancelOrder";

    private ObjectMapper objectMapper = new ObjectMapper();
    Logger logger = LoggerFactory.getLogger(ZbCoinTradeServiceImpl.class);
    private Integer successCode = 1000;
    private DecimalFormat priceFormat = new DecimalFormat("####.###");
    private DecimalFormat amountFormat = new DecimalFormat("####.##");

    @Override
    public String coinTrade(String symbol, TradeType tradeType, double price, double amount) {
        String dealPrice = priceFormat.format(price);
        String dealAmount = amountFormat.format(amount);
        PlatFormConfig zbPropertyConfig = PlatConfigContext.getCurrentConfig();
        if (zbPropertyConfig == null) {
            zbPropertyConfig = configRepository.selectByPlat(PlatType.ZB.getCode());
        }
        logger.info(String.format("ZB执行交易[%s]操作,交易币种[%s],价格[%s],交易量[%s]", tradeType.getName(), symbol, dealPrice, dealAmount));
        Map<String, String> params = new HashMap<>(10);
        String accessKey = zbPropertyConfig.getApiKey();
        String secretKey = zbPropertyConfig.getSecretKey();
        params.put("accesskey", accessKey);
        String digest = Digests.SHA(secretKey, null).toLowerCase();
        params.put("method", tradeMethod);
        params.put("amount", dealAmount);
        params.put("currency", symbol);
        params.put("price", dealPrice);
        params.put("tradeType", tradeType.getValue());

        // 参数按照ASCII值排序
        String sortParam = SortMapUtil.toStringMap(params);
        String sign = Digests.hmacSign(sortParam, digest, "MD5").toLowerCase();

        params.put("sign", sign);
        params.put("reqTime", System.currentTimeMillis() + "");
        String json = HttpUtil.getInstance().requestHttpPost(tradeUrl, params);
        logger.info("ZB下单操作返回:{}", json);
        String orderId = null;
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            Integer code = rootNode.get("code").asInt();
            OrderRecord orderRecord = null;
            if (successCode.equals(code)) {
                orderId = rootNode.get("id").asText();
                orderRecord = new OrderRecord(symbol, tradeType, OrderType.COMPLETED, price, amount);
                orderRecord.setOrderId(orderId);
            } else {
                String message = rootNode.get("message").asText();
                logger.info("ZB下单交易出错,错误信息:{}", message);
            }
            orderRecordRepository.save(orderRecord);

        } catch (IOException e) {
            logger.error("ZB下单出错,{}", ExceptionUtils.getStackTrace(e));
        }
        return orderId;
    }

    @Override
    public boolean cancelTrade(String symbol, String orderId) {
        PlatFormConfig zbPropertyConfig = PlatConfigContext.getCurrentConfig();
        if (zbPropertyConfig == null) {
            zbPropertyConfig = configRepository.selectByPlat(PlatType.ZB.getCode());
        }
        Map<String, String> params = new HashMap<>(10);
        String accessKey = zbPropertyConfig.getApiKey();
        String secretKey = zbPropertyConfig.getSecretKey();
        params.put("accesskey", accessKey);
        String digest = Digests.SHA(secretKey, null).toLowerCase();
        params.put("method", cancelMethod);
        params.put("currency", symbol);
        params.put("id", orderId);

        // 参数按照ASCII值排序
        String sortParam = SortMapUtil.toStringMap(params);
        String sign = Digests.hmacSign(sortParam, digest, "MD5").toLowerCase();

        params.put("sign", sign);
        params.put("reqTime", System.currentTimeMillis() + "");
        try {
            String json = HttpUtil.getInstance().requestHttpPost(cancelUrl, params);
            logger.info("ZB取消订单返回:{}", json);
            JsonNode rootNode = objectMapper.readTree(json);
            Integer code = rootNode.get("code").asInt();
            if (successCode.equals(code)) {
                //成功
                return true;
            } else {
                String message = rootNode.get("message").asText();
                logger.info("ZB取消出错,错误信息:{}", message);
            }
        } catch (IOException e) {
            logger.error("ZB取消订单出错,{}", ExceptionUtils.getStackTrace(e));
        }
        return false;
    }
}
