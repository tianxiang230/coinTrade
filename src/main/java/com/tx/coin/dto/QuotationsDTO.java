package com.tx.coin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tx.coin.entity.Quotations;
import com.tx.coin.utils.DateUtil;

import java.util.Date;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
public class QuotationsDTO {
    private Ticker ticker;
    /**
     * 返回时间
     */
    private String date;

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Quotations toEntity() {
        Quotations quotations = new Quotations();
        quotations.setBuy(Double.valueOf(ticker.getBuy()));
        quotations.setDate(DateUtil.getFormatDateTime(Long.valueOf(getDate())));
        quotations.setHigh(Double.valueOf(ticker.getHigh()));
        quotations.setLast(Double.valueOf(ticker.getLast()));
        quotations.setLow(Double.valueOf(ticker.getLow()));
        quotations.setVol(Double.valueOf(ticker.getVol()));
        quotations.setSell(Double.valueOf(ticker.getSell()));
        return quotations;
    }

    public static class Ticker {
        /**
         * 买一价
         */
        private String buy;
        /**
         * 最高价
         */
        private String high;
        /**
         * 最新成交价
         */
        private String last;
        /**
         * 最低价
         */
        private String low;
        /**
         * 卖一价
         */
        private String sell;
        /**
         * 最近24小时成交量
         */
        private String vol;

        public String getBuy() {
            return buy;
        }

        public void setBuy(String buy) {
            this.buy = buy;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getSell() {
            return sell;
        }

        public void setSell(String sell) {
            this.sell = sell;
        }

        public String getVol() {
            return vol;
        }

        public void setVol(String vol) {
            this.vol = vol;
        }
    }
}
