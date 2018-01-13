package com.tx.coin.enums;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Title TradeType
 * @Package com.tx.coin.enums
 * @Description 交易类型
 * @date 2018-1-12 14:59
 */
public enum TradeType {

    SELL("sell", "卖出"),
    BUY("buy", "买入");

    private String code;
    private String name;

    TradeType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
