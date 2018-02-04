package com.tx.coin.enums;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.enums
 * @Description 订单查询类型，0:未完成的订单 1:已经完成的订单
 * @date 2018-1-12 15:02
 */
public enum OrderType {

    NOT_COMPLETE("0", "未完成订单"),
    COMPLETED("1", "已完成订单");
    private String code;
    private String name;

    OrderType(String code, String name) {
        this.code = code;
        this.name = name();
    }

    public String getValue() {
        return code;
    }

    public String getName() {
        return name;
    }
}
