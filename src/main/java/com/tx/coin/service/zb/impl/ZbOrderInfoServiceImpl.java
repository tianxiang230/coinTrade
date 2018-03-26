package com.tx.coin.service.zb.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.context.PlatConfigContext;
import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.entity.PlatFormConfig;
import com.tx.coin.enums.PlatType;
import com.tx.coin.enums.TradeType;
import com.tx.coin.repository.PlatFormConfigRepository;
import com.tx.coin.service.IOrderInfoService;
import com.tx.coin.utils.DateUtil;
import com.tx.coin.utils.Digests;
import com.tx.coin.utils.HttpUtil;
import com.tx.coin.utils.SortMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.zb.impl
 * @Description
 * @date 2018-2-5 20:17
 */
@Service
public class ZbOrderInfoServiceImpl implements IOrderInfoService {
    @Autowired
    private PlatFormConfigRepository configRepository;
    private final String remoteUrl = "https://trade.bitkk.com/api/getUnfinishedOrdersIgnoreTradeType";
    private final String method = "getUnfinishedOrdersIgnoreTradeType";
    private final String defaultPageIndex = "1";
    private final String defaultPageSize = "10";
    private Logger logger = LoggerFactory.getLogger(ZbOrderInfoServiceImpl.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<OrderInfoDTO> getOpenOrderInfo(String orderId, String symbol) {
        PlatFormConfig zbPropertyConfig = PlatConfigContext.getCurrentConfig();
        if (zbPropertyConfig == null) {
            zbPropertyConfig = configRepository.selectByPlat(PlatType.ZB.getCode());
        }
        Map<String, String> params = new HashMap<>(10);
        String accessKey = zbPropertyConfig.getApiKey();
        String secretKey = zbPropertyConfig.getSecretKey();
        params.put("accesskey", accessKey);
        String digest = Digests.SHA(secretKey, null).toLowerCase();
        params.put("method", method);
        params.put("currency", symbol);
        params.put("pageIndex", defaultPageIndex);
        params.put("pageSize", defaultPageSize);
        // 参数按照ASCII值排序
        String sortParam = SortMapUtil.toStringMap(params);
        String sign = Digests.hmacSign(sortParam, digest, "MD5").toLowerCase();

        params.put("sign", sign);
        params.put("reqTime", System.currentTimeMillis() + "");
        try {
            String json = HttpUtil.getInstance().requestHttpPost(remoteUrl, params);
            logger.info("ZB获取交易中订单返回:{}", json);
            JsonNode rootNode = objectMapper.readTree(json);
            if (rootNode.isArray()) {
                List<OrderInfoDTO> result = new ArrayList<>();
                Iterator<JsonNode> elements = rootNode.elements();
                while (elements.hasNext()) {
                    JsonNode orderNode = elements.next();
                    OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
                    orderInfoDTO.setDealAmount(orderNode.get("trade_amount").asDouble());
                    orderInfoDTO.setCreateDate(new Date(orderNode.get("trade_date").asLong()));
                    orderInfoDTO.setType(TradeType.getCodeByValue(orderNode.get("type").asText()));
                    orderInfoDTO.setOrderId(orderNode.get("id").asText());
                    orderInfoDTO.setPrice(orderNode.get("price").asDouble());
                    orderInfoDTO.setStatus(orderNode.get("status").asInt());
                    orderInfoDTO.setSymbol(orderNode.get("currency").asText());
                    orderInfoDTO.setAmount(orderNode.get("total_amount").asDouble());
                    result.add(orderInfoDTO);
                }
                return result;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         currency : 交易类型
         id : 委托挂单号
         price : 单价
         status : 挂单状态(0：待成交,1：取消,2：交易完成,3：待成交未交易部份)
         total_amount : 挂单总数量
         trade_amount : 已成交数量
         trade_date : 委托时间
         trade_money : 已成交总金额
         trade_price : 成交均价
         type : 挂单类型 1/0[buy/sell]
         */
        return null;
    }
}
