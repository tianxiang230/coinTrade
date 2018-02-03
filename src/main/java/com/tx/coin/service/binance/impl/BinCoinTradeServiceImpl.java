package com.tx.coin.service.binance.impl;

import com.alibaba.fastjson.JSON;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderType;
import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.exception.BinanceApiException;
import com.tx.coin.entity.OrderRecord;
import com.tx.coin.enums.TradeType;
import com.tx.coin.repository.OrderRecordRepository;
import com.tx.coin.service.ICoinTradeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;


/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.binance.impl
 * @Description
 * @date 2018-1-31 18:25
 */
@Service
public class BinCoinTradeServiceImpl implements ICoinTradeService {
    private Logger logger = LoggerFactory.getLogger(BinCoinTradeServiceImpl.class);
    @Autowired
    private BinanceApiRestClient restClient;
    @Autowired
    private OrderRecordRepository orderRecordRepository;
    private DecimalFormat decimalFormat = new DecimalFormat("####.#######");
    private DecimalFormat amountFormat = new DecimalFormat("####.##");

    @Override
    public String coinTrade(String symbol, TradeType tradeType, double price, double amount) {
        String dealPrice = decimalFormat.format(price);
        String dealAmount = amountFormat.format(amount);
        logger.info("币安请求[" + (tradeType == TradeType.BUY ? "购买" : "出售") + "]交易,symbol:{},数量:{},价格:{}", new Object[]{symbol, dealAmount, dealPrice});
        if (StringUtils.isBlank(symbol) || price <= 0 || amount <= 0) {
            logger.info("交易参数不合法,symbol:{},price:{},amount:{}", new Object[]{symbol, dealPrice, dealAmount});
            return null;
        }
        if (price == 0 || amount == 0) {
            logger.info("交易价格或交易量为0,禁止交易");
            return null;
        }
        OrderSide orderSide = tradeType == TradeType.SELL ? OrderSide.SELL : OrderSide.BUY;
        NewOrder newOrder = new NewOrder(symbol, orderSide, OrderType.LIMIT, TimeInForce.GTC, dealAmount, dealPrice);
        NewOrderResponse orderResponse = null;
        try {
            orderResponse = restClient.newOrder(newOrder);
        } catch (BinanceApiException e) {
            logger.info("币安交易失败:{}", ExceptionUtils.getStackTrace(e));
            return null;
        }
        logger.info("币安交易响应:{}", JSON.toJSONString(orderResponse));
        String orderId = orderResponse.getOrderId().toString();
        OrderRecord orderRecord = new OrderRecord(symbol, tradeType, com.tx.coin.enums.OrderType.COMPLETED, price, amount);
        orderRecord.setOrderId(orderId);
        orderRecordRepository.save(orderRecord);
        return orderId;
    }

    @Override
    public boolean cancelTrade(String symbol, String orderId) {
        logger.info("币安请求取消订单,symbol:{},orderId:{}", symbol, orderId);
        try {
            restClient.cancelOrder(new CancelOrderRequest(symbol, Long.valueOf(orderId)));
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }
}
