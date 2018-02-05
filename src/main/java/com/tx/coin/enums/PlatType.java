package com.tx.coin.enums;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.enums
 * @Description 平台类型
 * @date 2018-2-1 13:33
 */
public enum PlatType {
    BIN("bin", "币安"),
    OKXE("okxe", "OKXE"),
    ZB("zb", "ZB");
    private String code;
    private String name;

    PlatType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static PlatType getType(String plat) {
        for (PlatType platType : PlatType.values()) {
            if (platType.getCode().equals(plat)) {
                return platType;
            }
        }
        return null;
    }
}
