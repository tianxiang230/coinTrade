package com.tx.coin.service.binance.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.service.IOrderInfoService;
import com.tx.coin.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.binance.impl
 * @Description
 * @date 2018-1-31 19:34
 */
@Service
public class BinOrderInfoServiceImpl implements IOrderInfoService {
    @Autowired
    private BinanceApiRestClient restClient;
    private Logger logger = LoggerFactory.getLogger(BinOrderInfoServiceImpl.class);

    @Override
    public List<OrderInfoDTO> getOpenOrderInfo(String orderId, String symbol) {
        OrderRequest orderRequest = new OrderRequest("INSBTC");
        List<Order> openOrders = restClient.getOpenOrders(orderRequest);
        logger.info("币安获取交易中订单信息:{}", JsonMapper.nonDefaultMapper().toJson(openOrders));
        List<OrderInfoDTO> result = new ArrayList<>();
        for (Order order : openOrders) {
            OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
            orderInfoDTO.setSymbol(order.getSymbol());
            orderInfoDTO.setStatus(0);
            orderInfoDTO.setOrderId(order.getOrderId().toString());
            orderInfoDTO.setAmount(Double.valueOf(order.getOrigQty()));
            orderInfoDTO.setPrice(Double.valueOf(order.getPrice()));
            orderInfoDTO.setType(order.getSide().name().toLowerCase());
            orderInfoDTO.setCreateDate(new Date(order.getTime()));
            orderInfoDTO.setDealAmount(Double.valueOf(order.getExecutedQty()));
            result.add(orderInfoDTO);
        }
        return result;
    }
}
