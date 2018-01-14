package com.tx.coin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.dto
 * @Description
 * @date 2018-1-13 22:51
 */
public class KLineDataDTO {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private Double open;

    private Double high;

    private Double low;

    private Double close;

    private Double amount;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
