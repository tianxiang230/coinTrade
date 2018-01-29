package com.tx.coin.service.impl;

import com.tx.coin.config.OkxePropertyConfig;
import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.dto.UserInfoDTO;
import com.tx.coin.enums.OrderStateEnum;
import com.tx.coin.enums.TradeType;
import com.tx.coin.service.okxe.*;
import com.tx.coin.utils.MathUtil;
import com.tx.coin.utils.PriceUtil;
import com.tx.coin.ws.api.ITradeRecordWsService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
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
    private ICoinQuotationService quotationService;
    @Autowired
    private OkxePropertyConfig okxePropertyConfig;
    @Autowired
    private ICoinTradeService tradeService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IOrderInfoService orderInfoService;
    @Autowired
    private ITradeRecordWsService tradeRecordWsService;
    @Value("${trade.wait.second}")
    private Integer waitSecond;
    private DecimalFormat decimalFormat = new DecimalFormat("####.########");

    /**
     * 成交时间
     */
    private Date t1 = null;
    private Logger logger = LoggerFactory.getLogger(OperatorServiceImpl.class);

    @Override
    public void operate() {
        try {
            String symbol = okxePropertyConfig.getU1() + "_" + okxePropertyConfig.getU2();
            List<Double> prices = quotationService.getLocalNewPrice(symbol);

            //前20个价格均值
            double mb = MathUtil.avg(prices);
            //取得标准差

            double adv = PriceUtil.calcuMd(prices);
//        double ub = mb + 2 * adv;
            double lb = mb - 2 * adv;
            logger.info("买入lb:{}", lb);
            UserInfoDTO userInfo = userInfoService.getUserInfo();
            Map<String, Object> userInfoMap = null;
            try {
                userInfoMap = BeanUtils.describe(userInfo);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            if (userInfoMap != null) {
                //底仓方账户余额
                Double d2 = Double.valueOf(userInfoMap.get(okxePropertyConfig.getU1()).toString());
                if (d2 == null) {
                    logger.info("获取{}余额失败", okxePropertyConfig.getU1());
                    return;
                } else {
                    logger.info("获取{}余额为:{}", okxePropertyConfig.getU1(), decimalFormat.format(d2));
                }
                //获取ASK方账户余额
                Double d4 = Double.valueOf(userInfoMap.get(okxePropertyConfig.getU2()).toString());
                if (d4 == null) {
                    logger.info("获取{}余额失败", okxePropertyConfig.getU2());
                    return;
                } else {
                    logger.info("获取{}余额为:{}", okxePropertyConfig.getU2(), decimalFormat.format(d4));
                }
                if (d2.doubleValue() == okxePropertyConfig.getD1().doubleValue()) {
                    //记录时间
                    t1 = new Date();
                }
                if (okxePropertyConfig.getD1().doubleValue() > d2.doubleValue() || okxePropertyConfig.getD3() > d4.doubleValue()) {
                    logger.info(String.format("余额不足,终止运行,d1=%f,d2=%f,d3=%f,d4=%f", okxePropertyConfig.getD1(), d2, okxePropertyConfig.getD3(), d4));
                    //不运行
                    return;
                }
                if (okxePropertyConfig.getD1().doubleValue() == d2.doubleValue()) {
                    buy(symbol, prices.get(0), lb);
                } else if (okxePropertyConfig.getD1().doubleValue() < d2.doubleValue()) {
                    //取消所有订单
                    String[] successOrderIds = getCancelOrders(orderInfoService.getOrderInfo("-1", symbol));
                    if (successOrderIds != null) {
                        for (int i = 0; i < successOrderIds.length; i++) {
                            String orders = successOrderIds[i];
                            logger.info("取消订单号为:{}", orders);
                            if (StringUtils.isBlank(orders) || "\"\"".equals(orders)) {
                                continue;
                            }
                            tradeService.cancelTrade(symbol, orders);
                        }
                        //5秒后卖出
                        try {
                            Thread.sleep(waitSecond * 1000);
                            logger.info("等待{}秒", waitSecond);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //取消订单后等待几秒钟再重新获取余额
                        userInfo = userInfoService.getUserInfo();
                        try {
                            userInfoMap = BeanUtils.describe(userInfo);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        if (userInfoMap != null) {
                            //底仓方账户余额
                            d2 = Double.valueOf(userInfoMap.get(okxePropertyConfig.getU1()).toString());
                            if (d2 == null) {
                                logger.info("重新获取{}余额失败", okxePropertyConfig.getU1());
                                return;
                            } else {
                                logger.info("重新获取{}余额为:{}", okxePropertyConfig.getU1(), decimalFormat.format(d2));
                            }
                        }
                        //重新获取余额结束
                    }

                    buy(symbol, prices.get(0), lb);
                    double sellAmount = d2 - propertyConfig.getD1();
                    double perAmount = sellAmount / 5.0;
                    //获取整点的收盘价
                    prices = quotationService.getHourPrice(symbol);
                    //重新计算整点标准差
                    adv = PriceUtil.calcuMd(prices);
                    mb = MathUtil.avg(prices);
                    double ub = mb + 2 * adv;
                    logger.info("标准差:{},平均值:{},卖出UB:{}", new Object[]{adv, mb, ub});
                    tradeService.coinTrade(symbol, TradeType.SELL, ub * okxePropertyConfig.getY1(), perAmount);
                    tradeService.coinTrade(symbol, TradeType.SELL, ub * okxePropertyConfig.getY2(), perAmount);
                    tradeService.coinTrade(symbol, TradeType.SELL, ub * okxePropertyConfig.getY3(), perAmount);
                    tradeService.coinTrade(symbol, TradeType.SELL, ub * okxePropertyConfig.getY4(), perAmount);
                    tradeService.coinTrade(symbol, TradeType.SELL, ub * okxePropertyConfig.getY5(), sellAmount - 4.0 * perAmount);
                }
            } else {
                logger.info("获取余额失败");
            }
        } catch (Exception e) {
            logger.info("用户资金操作出错,错误信息:{}", ExceptionUtils.getStackTrace(e));
        }
    }


    private void buy(String symbol, Double currentPrice, Double lb) {
        if (currentPrice != null) {
            if (currentPrice > lb) {
                //在LB价格位置买入S1手
                tradeService.coinTrade(symbol, TradeType.BUY, lb, okxePropertyConfig.getS1());
            } else {
                //在D2*(1-B1)位置买入S1手
                double buyPrice = currentPrice * okxePropertyConfig.getB1();
                tradeService.coinTrade(symbol, TradeType.BUY, buyPrice, okxePropertyConfig.getS1());
            }
            //记录成交时间
            t1 = new Date();
        }
    }

    //    @PostConstruct
    private void regRecord() {
        logger.info("订阅交易记录");
        tradeRecordWsService.tradeRecord(okxePropertyConfig.getU1() + "_" + okxePropertyConfig.getU2());
    }

    public static String[] getCancelOrders(List<OrderInfoDTO> orders) {
        if (orders.size() <= 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (OrderInfoDTO order : orders) {
            Integer state = order.getStatus();
            if (state.equals(OrderStateEnum.PART_DEAL.getValue()) || state.equals(OrderStateEnum.UNDEAL.getValue())) {
                stringBuilder.append(order.getOrderId());
                stringBuilder.append(",");
            }
        }
        if (stringBuilder.length() <= 0) {
            return null;
        }
        String allIds = stringBuilder.substring(0, stringBuilder.length() - 1);
        String[] ids = allIds.split(",");
        int lenth = ids.length;
        int arrayLength = 0;
        if (lenth % 3 == 0) {
            arrayLength = lenth / 3;
        } else {
            arrayLength = lenth / 3 + 1;
        }
        String[] orderIds = new String[arrayLength];
        int index = -1;
        stringBuilder = new StringBuilder();
        for (int i = 0; i < lenth; i++) {
            if (i > 0 && i % 3 == 0) {
                orderIds[++index] = stringBuilder.substring(0, stringBuilder.length() - 1);
                stringBuilder = new StringBuilder();
            }
            stringBuilder.append(ids[i]).append(",");
        }
        if (stringBuilder.length() > 0) {
            orderIds[++index] = stringBuilder.substring(0, stringBuilder.length() - 1);
        }
        return orderIds;
    }
}
