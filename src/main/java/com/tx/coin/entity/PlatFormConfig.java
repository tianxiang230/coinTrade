package com.tx.coin.entity;

import com.tx.coin.utils.JsonMapper;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.entity
 * @Description
 * @date 2018-2-1 13:14
 */
@Table(name = "platform_config")
@Entity
public class PlatFormConfig extends BaseEntity {
    /**
     * 平台类型
     */
    private String plat;

    private String apiKey;

    private String secretKey;
    /**
     * 是否交易
     */
    private Boolean tradeOrNot;
    /**
     * 底仓方
     */
    private String u1;
    /**
     * ASK方
     */
    private String u2;
    /**
     * 底仓数量
     */
    private Double d1;

    private Double d3;
    /**
     * 基本手数
     */
    private Double s1;
    /**
     * 浮盈率
     */
    private Double y1, y2, y3, y4, y5;
    /**
     * 补仓位置
     */
    private Double b1;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getU1() {
        return u1;
    }

    public void setU1(String u1) {
        this.u1 = u1;
    }

    public String getU2() {
        return u2;
    }

    public void setU2(String u2) {
        this.u2 = u2;
    }

    public Double getD1() {
        return d1;
    }

    public void setD1(Double d1) {
        this.d1 = d1;
    }

    public Double getD3() {
        return d3;
    }

    public void setD3(Double d3) {
        this.d3 = d3;
    }

    public Double getS1() {
        return s1;
    }

    public void setS1(Double s1) {
        this.s1 = s1;
    }

    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    public Double getY2() {
        return y2;
    }

    public void setY2(Double y2) {
        this.y2 = y2;
    }

    public Double getY3() {
        return y3;
    }

    public void setY3(Double y3) {
        this.y3 = y3;
    }

    public Double getY4() {
        return y4;
    }

    public void setY4(Double y4) {
        this.y4 = y4;
    }

    public Double getY5() {
        return y5;
    }

    public void setY5(Double y5) {
        this.y5 = y5;
    }

    public Double getB1() {
        return b1;
    }

    public void setB1(Double b1) {
        this.b1 = b1;
    }

    public Boolean getTradeOrNot() {
        return tradeOrNot;
    }

    public void setTradeOrNot(Boolean tradeOrNot) {
        this.tradeOrNot = tradeOrNot;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    @Override
    public String toString() {
        return JsonMapper.nonDefaultMapper().toJson(this);
    }
}
