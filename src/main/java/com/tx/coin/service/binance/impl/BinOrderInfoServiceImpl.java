package com.tx.coin.service.binance.impl;

import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.service.IOrderInfoService;

import java.util.List;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.binance.impl
 * @Description
 * @date 2018-1-31 19:34
 */
public class BinOrderInfoServiceImpl implements IOrderInfoService {
    @Override
    public List<OrderInfoDTO> getBatchOrdersInfo(String orderId, int type, String symbol) {
        return null;
    }

    @Override
    public List<OrderInfoDTO> getOrderInfo(String orderId, String symbol) {
        return null;
    }
}
