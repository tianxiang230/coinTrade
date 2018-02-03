package com.tx.coin.service.binance.impl;

import com.tx.coin.context.PlatConfigContext;
import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.entity.PlatFormConfig;
import com.tx.coin.enums.PlatType;
import com.tx.coin.enums.TradeType;
import com.tx.coin.repository.PlatFormConfigRepository;
import com.tx.coin.service.ICoinTradeService;
import com.tx.coin.service.IOperatorService;
import com.tx.coin.service.IOrderInfoService;
import com.tx.coin.service.IUserInfoService;
import com.tx.coin.service.common.IQuotationCommonService;
import com.tx.coin.utils.MathUtil;
import com.tx.coin.utils.PriceUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.binance.impl
 * @Description
 * @date 2018-1-31 19:30
 */
@Service
public class BinOperatorServiceServiceImpl implements IOperatorService {
    private Logger logger = LoggerFactory.getLogger(BinOperatorServiceServiceImpl.class);
    @Autowired
    private IQuotationCommonService quotationCommonService;
    @Autowired
    @Qualifier(value = "binUserInfoServiceImpl")
    private IUserInfoService userInfoService;
    @Autowired
    @Qualifier(value = "binCoinTradeServiceImpl")
    private ICoinTradeService tradeService;
    @Autowired
    private PlatFormConfigRepository configRepository;
    @Autowired
    @Qualifier(value = "binOrderInfoServiceImpl")
    private IOrderInfoService orderInfoService;

    @Value("${trade.wait.second}")
    private Integer waitSecond;
    private DecimalFormat decimalFormat = new DecimalFormat("####.########");

    @Override
    public void operate() {
        PlatFormConfig binPropertyConfig = PlatConfigContext.getCurrentConfig();
        try {
            String symbol = binPropertyConfig.getU1() + binPropertyConfig.getU2();
            List<Double> prices = quotationCommonService.getLocalNewPrice(symbol, PlatType.BIN);
            if (prices.size() < IQuotationCommonService.DATA_SIZE) {
                binPropertyConfig.setTradeOrNot(false);
                configRepository.save(binPropertyConfig);
                throw new RuntimeException("抓取数据不足,自动关闭交易" + IQuotationCommonService.DATA_SIZE);
            }
            //前20个价格均值
            double mb = MathUtil.avg(prices);
            //取得标准差

            double adv = PriceUtil.calcuMd(prices);
//        double ub = mb + 2 * adv;
            double lb = mb - 2 * adv;
            logger.info("买入lb:{}", lb);
            Map<String, Object> userInfoMap = userInfoService.getUserInfo();

            if (userInfoMap != null) {
                //底仓方账户余额
                Double d2 = Double.valueOf(userInfoMap.get(binPropertyConfig.getU1()).toString());
                if (d2 == null) {
                    logger.info("币安获取{}余额失败", binPropertyConfig.getU1());
                    return;
                } else {
                    logger.info("币安获取{}余额为:{}", binPropertyConfig.getU1(), decimalFormat.format(d2));
                }
                //获取ASK方账户余额
                Double d4 = Double.valueOf(userInfoMap.get(binPropertyConfig.getU2()).toString());
                if (d4 == null) {
                    logger.info("币安获取{}余额失败", binPropertyConfig.getU2());
                    return;
                } else {
                    logger.info("币安获取{}余额为:{}", binPropertyConfig.getU2(), decimalFormat.format(d4));
                }
                if (binPropertyConfig.getD1().doubleValue() > d2.doubleValue() || binPropertyConfig.getD3() > d4.doubleValue()) {
                    logger.info(String.format("余额不足,终止运行,d1=%f,d2=%f,d3=%f,d4=%f", binPropertyConfig.getD1(), d2, binPropertyConfig.getD3(), d4));
                    //不运行
                    return;
                }
                if (binPropertyConfig.getD1().doubleValue() == d2.doubleValue()) {
                    buy(symbol, prices.get(0), lb);
                } else if (binPropertyConfig.getD1().doubleValue() < d2.doubleValue()) {
                    //取消所有订单
                    List<OrderInfoDTO> orders = orderInfoService.getOpenOrderInfo("-1", symbol);
                    if (orders != null && orders.size() > 0) {
                        for (int i = 0; i < orders.size(); i++) {
                            String orderId = orders.get(i).getOrderId();
                            logger.info("币安取消订单号为:{}", orderId);
                            tradeService.cancelTrade(symbol, orderId);
                        }
                        //5秒后卖出
                        sleep(waitSecond);
                        //取消订单后等待几秒钟再重新获取余额
                        userInfoMap = userInfoService.getUserInfo();
                        if (userInfoMap != null) {
                            //底仓方账户余额
                            d2 = Double.valueOf(userInfoMap.get(binPropertyConfig.getU1()).toString());
                            if (d2 == null) {
                                logger.info("币安重新获取{}余额失败", binPropertyConfig.getU1());
                                return;
                            } else {
                                logger.info("币安重新获取{}余额为:{}", binPropertyConfig.getU1(), decimalFormat.format(d2));
                            }
                        }
                        //重新获取余额结束
                    }
                    buy(symbol, prices.get(0), lb);
                    double sellAmount = d2 - binPropertyConfig.getD1();
                    double perAmount = sellAmount / 5.0;
                    //获取整点的收盘价
                    prices = quotationCommonService.getHourPrice(symbol, PlatType.BIN);
                    if (prices.size() < IQuotationCommonService.DATA_SIZE) {
                        binPropertyConfig.setTradeOrNot(false);
                        configRepository.save(binPropertyConfig);
                        throw new RuntimeException("【币安】抓取数据不足,自动关闭交易" + IQuotationCommonService.DATA_SIZE);
                    }
                    //重新计算整点标准差
                    adv = PriceUtil.calcuMd(prices);
                    mb = MathUtil.avg(prices);
                    double ub = mb + 2 * adv;
                    logger.info("【币安】标准差:{},平均值:{},卖出UB:{}", new Object[]{decimalFormat.format(adv), decimalFormat.format(mb), decimalFormat.format(ub)});
                    tradeService.coinTrade(symbol, TradeType.SELL, ub * binPropertyConfig.getY1(), perAmount);
                    sleep(1);
                    tradeService.coinTrade(symbol, TradeType.SELL, ub * binPropertyConfig.getY2(), perAmount);
                    sleep(1);
                    tradeService.coinTrade(symbol, TradeType.SELL, ub * binPropertyConfig.getY3(), perAmount);
                    sleep(1);
                    tradeService.coinTrade(symbol, TradeType.SELL, ub * binPropertyConfig.getY4(), perAmount);
                    sleep(1);
                    tradeService.coinTrade(symbol, TradeType.SELL, ub * binPropertyConfig.getY5(), sellAmount - 4.0 * perAmount);
                }
            } else {
                logger.info("币安获取余额失败");
            }
        } catch (Exception e) {
            logger.info("币安用户资金操作出错,错误信息:{}", ExceptionUtils.getStackTrace(e));
        }
    }

    private void buy(String symbol, Double currentPrice, Double lb) {
        PlatFormConfig okxePropertyConfig = PlatConfigContext.getCurrentConfig();
        if (currentPrice != null && lb != null) {
            if (currentPrice > lb) {
                //在LB价格位置买入S1手
                tradeService.coinTrade(symbol, TradeType.BUY, lb, okxePropertyConfig.getS1());
            } else {
                //在D2*(1-B1)位置买入S1手
                double buyPrice = currentPrice * okxePropertyConfig.getB1();
                tradeService.coinTrade(symbol, TradeType.BUY, buyPrice, okxePropertyConfig.getS1());
            }
        }
    }

    private void sleep(int sleepSecond) {
        try {
            Thread.sleep(sleepSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
