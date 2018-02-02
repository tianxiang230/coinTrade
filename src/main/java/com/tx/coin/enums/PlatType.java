package com.tx.coin.enums;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.enums
 * @Description 平台类型
 * @date 2018-2-1 13:33
 */
public enum PlatType {
    BIN("bin"),
    OKXE("okxe");
    private String code;

    PlatType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
