package com.tx.coin.ws.api;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.ws.api
 * @Description websocket订阅交易记录
 * @date 2018-1-13 13:08
 */
public interface ITradeRecordWsService {
    /**
     * 订阅交易记录
     * @param symbol
     * @return
     */
    String tradeRecord(String symbol);
}
