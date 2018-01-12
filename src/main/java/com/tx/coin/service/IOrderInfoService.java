package com.tx.coin.service;

import com.tx.coin.dto.OrderInfoDTO;

/**
 * Created by 你慧快乐 on 2018-1-11.
 */
public interface IOrderInfoService {
    OrderInfoDTO getOrderInfo(String orderId,int type,String symbol);
}
