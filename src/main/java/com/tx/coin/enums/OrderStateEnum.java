package com.tx.coin.enums;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.enums
 * @Description 查询用的订单状态,-1：已撤销  0：未成交 1：部分成交 2：完全成交 4:撤单处理中
 * @date 2018-1-15 17:17
 */
public enum OrderStateEnum {
    CANCELED("已撤销", -1),
    UNDEAL("未成交", 0),
    PART_DEAL("部分成交", 1),
    DEAL("完全成交", 2),
    CANCELING("撤单处理中", 4),;

    private String name;
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    OrderStateEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
