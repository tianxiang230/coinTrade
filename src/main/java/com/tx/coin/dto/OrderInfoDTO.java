package com.tx.coin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by 你慧快乐 on 2018-1-11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderInfoDTO {
    /**
     * 订单号
     */
    @JsonProperty("order_id")
    private String orderId;
    /**
     * 订单状态 -1：已撤销  0：未成交 1：部分成交 2：完全成交 4:撤单处理中
     */
    private Integer status;
    /**
     * 货币类型
     */
    private String symbol;
    /**
     * 交易类型，sell或buy
     */
    private String type;
    /**
     * 限价单请求：委托价格 / 市价单请求：买入的usd金额
     */
    private Double price;
    /**
     * 限价单请求：下单数量 /市价单请求：卖出的btc/ltc数量
     */
    private Double amount;
    /**
     * 成交数量
     */
    @JsonProperty("dealAmount")
    private Double dealAmount;
    /**
     * 平均成交价
     */
    private Double avg_price;

    @JsonProperty("create_date")
    private Date createDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(Double dealAmount) {
        this.dealAmount = dealAmount;
    }

    public Double getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(Double avg_price) {
        this.avg_price = avg_price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
