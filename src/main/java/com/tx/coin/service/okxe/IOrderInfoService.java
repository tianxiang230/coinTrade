package com.tx.coin.service.okxe;

import com.tx.coin.dto.OrderInfoDTO;

import java.util.List;

/**
 * Created by 你慧快乐 on 2018-1-11.
 */
public interface IOrderInfoService {
    /**
     * 批量获取订单信息
     * @param orderId
     * @param type 查询类型 0:未完成的订单 1:已经完成的订单
     * @param symbol
     * @return
     */
    List<OrderInfoDTO> getBatchOrdersInfo(String orderId, int type, String symbol);

    /**
     * 获取订单信息，或未完成订单
     * @param orderId 订单ID -1:未完成订单，否则查询相应订单号的订单
     * @param symbol
     * @return
     */
    List<OrderInfoDTO> getOrderInfo(String orderId, String symbol);
}
