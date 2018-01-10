package com.tx.coin.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
@Table(name = "quotations")
@Entity
public class Quotations extends BaseEntity{
    /**
     * 货币股票种类
     */
    private String symbol;
    /**
     * 买一价
     */
    private Double buy;
    /**
     * 最高价
     */
    private Double high;
    /**
     * 最新成交价
     */
    private Double last;
    /**
     * 最低价
     */
    private Double low;
    /**
     * 卖一价
     */
    private Double sell;
    /**
     * 最近24小时成交量
     */
    private Double vol;
    /**
     * 返回时间
     */
    private Date date;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public Double getVol() {
        return vol;
    }

    public void setVol(Double vol) {
        this.vol = vol;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
