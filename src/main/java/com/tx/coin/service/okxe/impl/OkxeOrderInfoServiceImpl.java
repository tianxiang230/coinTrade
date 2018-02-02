package com.tx.coin.service.okxe.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.config.OkxePropertyConfig;
import com.tx.coin.context.PlatConfigContext;
import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.entity.PlatFormConfig;
import com.tx.coin.enums.ResponseCode;
import com.tx.coin.service.IOrderInfoService;
import com.tx.coin.utils.EncryptHelper;
import com.tx.coin.utils.HttpUtil;
import com.tx.coin.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 你慧快乐 on 2018-1-11.
 */
@Service
public class OkxeOrderInfoServiceImpl implements IOrderInfoService {
//    @Autowired
//    private OkxePropertyConfig okxePropertyConfig;

    @Value("${coin.remote.ordersinfo}")
    private String ordersInfoUrl;
    @Value("${coin.remote.orderinfo}")
    private String orderInfoUrl;

    private Logger logger = LoggerFactory.getLogger(OkxeOrderInfoServiceImpl.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<OrderInfoDTO> getBatchOrdersInfo(String orderId, int type, String symbol) {
        PlatFormConfig okxePropertyConfig = PlatConfigContext.getCurrentConfig();
        String apiKey = okxePropertyConfig.getApiKey();
        String secretKey = okxePropertyConfig.getSecretKey();
        Map<String, String> param = new HashMap<>(15);
        param.put("api_key", apiKey);
        param.put("symbol", symbol);
        param.put("type", String.valueOf(type));
        param.put("order_id", orderId);
        String sign = EncryptHelper.sign(param, secretKey, "utf-8");
        param.put("sign", sign);
        param.put("secret_key", secretKey);
        String result = null;
        try {
            result = HttpUtil.doPostSSL(ordersInfoUrl, param);
            logger.info("批量获取订单信息接口,请求:{},响应:{}", JsonMapper.nonDefaultMapper().toJson(param),result);
            JsonNode rootNode = objectMapper.readTree(result);
            boolean success = rootNode.get("result").asBoolean();
            if (success) {
                JsonNode ordersNode = rootNode.get("orders");
                String ordersStr = ordersNode.toString();
                List<OrderInfoDTO> orderList = objectMapper.readValue(ordersStr, new TypeReference<List<OrderInfoDTO>>() {
                });
                logger.info("批量获取订单接口获取到订单数量:{}", orderList.size());
                return orderList;
            } else {
                String errorCode = rootNode.get("error_code").asText();
                logger.info(ResponseCode.responseCode.get(errorCode));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderInfoDTO> getOpenOrderInfo(String orderId, String symbol) {
        PlatFormConfig okxePropertyConfig = PlatConfigContext.getCurrentConfig();
        String apiKey = okxePropertyConfig.getApiKey();
        String secretKey = okxePropertyConfig.getSecretKey();
        Map<String, String> param = new HashMap<>();
        param.put("api_key", apiKey);
        param.put("symbol", symbol);
        param.put("order_id", orderId);
        String sign = EncryptHelper.sign(param, secretKey, "utf-8");
        param.put("sign", sign);
        param.put("secret_key", secretKey);
        String result = null;
        try {
            result = HttpUtil.doPostSSL(orderInfoUrl, param);
            logger.info("获取订单信息接口,请求:{},响应:{}",JsonMapper.nonDefaultMapper().toJson(param),result);
            JsonNode rootNode = objectMapper.readTree(result);
            boolean success = rootNode.get("result").asBoolean();
            if (success) {
                JsonNode ordersNode = rootNode.get("orders");
                String ordersStr = ordersNode.toString();
                List<OrderInfoDTO> orderList = objectMapper.readValue(ordersStr, new TypeReference<List<OrderInfoDTO>>() {
                });
                logger.info("获取到订单数量:{}", orderList.size());
                return orderList;
            } else {
                String errorCode = rootNode.get("error_code").asText();
                logger.info(ResponseCode.responseCode.get(errorCode));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
