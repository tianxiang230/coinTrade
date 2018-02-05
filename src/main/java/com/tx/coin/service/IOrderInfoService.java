package com.tx.coin.service;

import com.tx.coin.dto.OrderInfoDTO;

import java.util.List;

/**
 * Created by 你慧快乐 on 2018-1-11.
 */
public interface IOrderInfoService {
    /**
     * 获取订单信息，或未完成订单
     * @param orderId 订单ID -1:未完成订单，否则查询相应订单号的订单
     * @param symbol
     * @return
     */
    List<OrderInfoDTO> getOpenOrderInfo(String orderId, String symbol);
}
