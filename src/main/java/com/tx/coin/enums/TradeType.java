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

    SELL("sell", "卖出", "0"),
    BUY("buy", "买入", "1");

    private String code;
    private String name;
    private String value;

    TradeType(String code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static String getCodeByValue(String value) {
        for (TradeType tradeType : TradeType.values()) {
            if (tradeType.getValue().equals(value)) {
                return tradeType.getCode();
            }
        }
        return null;
    }
}
