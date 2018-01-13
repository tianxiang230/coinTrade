package com.tx.coin.service.impl;

import com.tx.coin.config.PropertyConfig;
import com.tx.coin.dto.UserInfoDTO;
import com.tx.coin.enums.TradeType;
import com.tx.coin.repository.QuotationsRepository;
import com.tx.coin.service.*;
import com.tx.coin.utils.MathUtil;
import com.tx.coin.ws.TradeRecordAdapter;
import com.tx.coin.ws.api.ITradeRecordWsService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.impl
 * @Description
 * @date 2018-1-12 18:51
 */
@Component
public class OperatorServiceImpl implements IOperatorService {
    @Autowired
    private IPriceService priceService;
    @Autowired
    private ICoinQuotationService quotationService;
    @Autowired
    private PropertyConfig propertyConfig;
    @Autowired
    private ICoinTradeService tradeService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ITradeRecordWsService tradeRecordWsService;

    /**
     * 交易中的订单号
     */
    private List<String> tradeOrderIds = new ArrayList<>();
    //成交时间
    private Date t1;
    private Logger logger = LoggerFactory.getLogger(OperatorServiceImpl.class);

    @Override
    public void operate() {
        String symbol = propertyConfig.getU1() + "_" + propertyConfig.getU2();
        List<Double> prices = quotationService.getLocalNewPrice(symbol);
        //前19个价格均值
        double mb = MathUtil.avg(prices);
        //取得标准差
        double adv = priceService.calcuMd(prices);
        double ub = mb + 2 * adv;
        double lb = mb - 2 * adv;
        UserInfoDTO userInfo = userInfoService.getUserInfo();
        Map<String, Object> userInfoMap = null;
        try {
            userInfoMap = BeanUtils.describe(userInfo);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (userInfoMap != null) {
            //用户余额
            Double d2 = Double.valueOf (userInfoMap.get(propertyConfig.getU1()).toString());
            if (d2==null){
                logger.info("获取{}余额失败",propertyConfig.getU1());
                return;
            }
            if (d2.doubleValue()==propertyConfig.getD1().doubleValue()){
                //记录时间
                t1=new Date();
            }
            logger.info("获取{}余额为:{}", propertyConfig.getU1(), d2);
            if (propertyConfig.getD1().doubleValue() == d2.doubleValue()) {
                buy(symbol, d2, lb);
            } else if (propertyConfig.getD1().doubleValue() < d2.doubleValue()) {
                //取消所有订单
                String successOrderIds = StringUtils.join(tradeOrderIds, ",");
                tradeService.cancelTrade(symbol, successOrderIds);
                buy(symbol, d2, lb);
                //5秒后卖出
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //最新的余额
                d2 = (Double) (userInfoMap.get(propertyConfig.getU1()));
                double sellAmount = d2 - propertyConfig.getD1();
                double perAmount = sellAmount / 5.0;
                double advPrice = MathUtil.avg(quotationService.getHourPrice(symbol));
                tradeService.coinTrade(symbol, TradeType.SELL, advPrice * propertyConfig.getY1(), perAmount);
                tradeService.coinTrade(symbol, TradeType.SELL, advPrice * propertyConfig.getY2(), perAmount);
                tradeService.coinTrade(symbol, TradeType.SELL, advPrice * propertyConfig.getY3(), perAmount);
                tradeService.coinTrade(symbol, TradeType.SELL, advPrice * propertyConfig.getY4(), perAmount);
                tradeService.coinTrade(symbol, TradeType.SELL, advPrice * propertyConfig.getY5(), sellAmount - 4 * perAmount);
            }
        } else {
            logger.info("获取余额失败");
        }
    }


    private void buy(String symbol, Double d2, Double lb) {
        if (d2 != null) {
            String successOrderId = null;
            if (d2 > lb) {
                //在LB价格位置买入S1手
                successOrderId = tradeService.coinTrade(symbol, TradeType.BUY, lb, propertyConfig.getS1());
            } else {
                //在D2*(1-B1)位置买入S1手
                double buyPrice = d2 * (1 - propertyConfig.getB1());
                successOrderId = tradeService.coinTrade(symbol, TradeType.BUY, buyPrice, propertyConfig.getS1());
            }
            //记录成交时间
            t1 = new Date();
            //加入成功订单号
            tradeOrderIds.add(successOrderId);
        }
    }

//    @PostConstruct
    private void regRecord() {
        logger.info("订阅交易记录");
        tradeRecordWsService.tradeRecord(propertyConfig.getU1() + "_" + propertyConfig.getU2());
    }
}
