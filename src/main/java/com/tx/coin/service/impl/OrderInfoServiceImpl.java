package com.tx.coin.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.config.PropertyConfig;
import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.enums.ResponseCode;
import com.tx.coin.service.IOrderInfoService;
import com.tx.coin.utils.EncryptHelper;
import com.tx.coin.utils.HttpUtil;
import com.tx.coin.utils.HttpUtilManager;
import org.apache.http.HttpException;
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
public class OrderInfoServiceImpl implements IOrderInfoService {
    @Autowired
    private PropertyConfig propertyConfig;

    @Value("${coin.remote.orderinfo}")
    private String orderInfoUrl;

    private Logger logger= LoggerFactory.getLogger(OrderInfoServiceImpl.class);
    private ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public OrderInfoDTO getOrderInfo(String orderId,int type,String symbol) {
        String apiKey= propertyConfig.getApiKey();
        String secretKey= propertyConfig.getSecretKey();
        Map<String,String> param=new HashMap<>();
        param.put("api_key",apiKey);
        param.put("symbol",symbol);
        param.put("type",String.valueOf(type));
        param.put("order_id",orderId);
        String sign = EncryptHelper.sign(param,secretKey,"utf-8");
        param.put("sign",sign);
        param.put("secret_key",secretKey);
        String result=null;
        try {
            result = HttpUtil.doPostSSL(orderInfoUrl,param);
            JsonNode rootNode=objectMapper.readTree(result);
            boolean success=rootNode.get("result").asBoolean();
            if (success){
                JsonNode ordersNode=rootNode.get("orders");
                String ordersStr = ordersNode.toString();
                List<OrderInfoDTO> orderList = objectMapper.readValue(ordersStr,new TypeReference<List<OrderInfoDTO>>(){});
                logger.info("获取到订单数量:{}",orderList.size());
                return orderList.get(0);
            }else{
                String errorCode=rootNode.get("error_code").asText();
                logger.info(ResponseCode.responseCode.get(errorCode));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("币币交易返回:{}",result);
        return null;
    }
}
