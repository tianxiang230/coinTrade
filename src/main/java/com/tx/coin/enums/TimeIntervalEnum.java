package com.tx.coin.enums;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.enums
 * @Description
 * @date 2018-1-13 22:37
 */
public enum TimeIntervalEnum {
    ONE_MIN("1分钟", "1min"),
    THREE_MIN("3分钟", "3min"),
    FIVE_MIN("5分钟", "5min"),
    FIF_MIN("15分钟", "15min"),
    THIRTY_MIN("30分钟", "30min"),
    OME_DAY("'1天", "1day"),
    THREE_DAY("3天", "3day"),
    ONE_WEEK("1周", "1week"),
    ONE_HOUR("1小时", "1hour"),
    TWO_HOUR("2小时", "2hour"),
    FOUR_HOUR("4小时", "4hour"),
    SIX_HOUR("6小时", "6hour"),
    TWELVE_HOUR("12小时", "12hour");
    private String value;
    private String name;

    TimeIntervalEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
