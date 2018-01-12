package com.tx.coin.service;

import com.tx.coin.enums.TradeType;

/**
 * 币币下单交易接口
 * Created by 你慧快乐 on 2018-1-10.
 */
public interface ICoinTradeService {
    /**
     * 下单交易，买入或卖出
     * @param symbol 货币类型
     * @param type 交易类型，买buy，或卖sell
     * @param price 下单价格
     * @param amount 交易数量
     * @return
     */
    boolean coinTrade(String symbol, TradeType type, double price, double amount);

}
