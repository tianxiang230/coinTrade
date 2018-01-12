package com.tx.coin.enums;

/**
* @Title TradeType
* @Package com.tx.coin.enums
* @Description 交易类型
* @author 你慧快乐
* @date 2018-1-12 14:59
* @version V1.0
*/
public enum TradeType {

    SELL("sell"),
    BUY("buy");

    private String code;

    TradeType(String code){
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
