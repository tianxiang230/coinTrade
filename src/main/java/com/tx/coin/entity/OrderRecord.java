package com.tx.coin.entity;

import com.tx.coin.enums.OrderType;
import com.tx.coin.enums.TradeType;
import com.tx.coin.utils.DateUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 你慧快乐 on 2018-1-12.
 */
@Entity
@Table(name = "order_record")
public class OrderRecord extends BaseEntity {
    public OrderRecord(){}

    public OrderRecord(String symbol,TradeType tradeType,OrderType orderType,double price,double amount){
        super.setCreateDate(DateUtil.getFormatDateTime(new Date()));
        this.amount=amount;
        this.price=price;
        this.orderType=orderType;
        this.tradeType=tradeType;
        this.symbol=symbol;
    }
    @Column(name = "order_id",length = 15)
    private String orderId;

    @Column(name = "trade_type")
    @Enumerated
    private TradeType tradeType;

    @Column(name = "order_type")
    @Enumerated(value = EnumType.STRING)
    private OrderType orderType;

    @Column(nullable=false,length = 15)
    private String symbol;

    private double price;

    private double amount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
