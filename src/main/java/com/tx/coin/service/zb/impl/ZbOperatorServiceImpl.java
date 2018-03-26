package com.tx.coin.service.zb.impl;

import com.tx.coin.context.PlatConfigContext;
import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.entity.PlatFormConfig;
import com.tx.coin.enums.PlatType;
import com.tx.coin.enums.TradeType;
import com.tx.coin.repository.PlatFormConfigRepository;
import com.tx.coin.service.*;
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

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.zb.impl
 * @Description
 * @date 2018-2-5 20:30
 */
@Service
public class ZbOperatorServiceImpl extends BaseOperatorService implements IOperatorService {
    @Autowired
    private IQuotationCommonService quotationCommonService;
    @Autowired
    @Qualifier(value = "zbCoinTradeServiceImpl")
    private ICoinTradeService tradeService;
    @Autowired
    @Qualifier(value = "zbCoinUserInfoServiceImpl")
    private IUserInfoService userInfoService;
    @Autowired
    private PlatFormConfigRepository configRepository;
    @Autowired
    @Qualifier(value = "zbOrderInfoServiceImpl")
    private IOrderInfoService orderInfoService;

    @Value("${trade.wait.second}")
    private Integer waitSecond;
    private DecimalFormat decimalFormat = new DecimalFormat("####.########");
    private Logger logger = LoggerFactory.getLogger(ZbOperatorServiceImpl.class);


    @Override
    protected ICoinTradeService getTradeService() {
        return tradeService;
    }

    @Override
    public void operate() {
        PlatFormConfig zbPropertyConfig = PlatConfigContext.getCurrentConfig();
        try {
            String symbol = zbPropertyConfig.getU1() + "_" + zbPropertyConfig.getU2();
            List<Double> prices = quotationCommonService.getHourPrice(symbol, PlatType.ZB);
            if (prices.size() < IQuotationCommonService.DATA_SIZE) {
                zbPropertyConfig.setTradeOrNot(false);
                configRepository.save(zbPropertyConfig);
                throw new RuntimeException("抓取数据不足,自动关闭交易" + IQuotationCommonService.DATA_SIZE);
            }
            //前20个价格均值
            double mb = MathUtil.avg(prices);
            //取得标准差
            double adv = PriceUtil.calcuMd(prices);
            double ub = mb + 2 * adv;
            double lb = mb - 2 * adv;
            logger.info("ZB买入lb:{}", lb);
            //取消所有订单
            List<OrderInfoDTO> orders = orderInfoService.getOpenOrderInfo("-1", symbol);
            cancelOrders(orders, symbol);
            buy(symbol, lb + 1, lb);
            logger.info("【ZB】标准差:{},平均值:{},卖出UB:{}", new Object[]{decimalFormat.format(adv), decimalFormat.format(mb), decimalFormat.format(ub)});
            //此处的B1就是S2，只有ZB平台才是如此
            tradeService.coinTrade(symbol, TradeType.SELL, ub, zbPropertyConfig.getB1());

        } catch (Exception e) {
            logger.info("ZB用户资金操作出错,错误信息:{}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 取消订单并返回最新余额
     *
     * @param orders
     * @param symbol
     * @return
     */
    private Double cancelOrders(List<OrderInfoDTO> orders, String symbol) {
        Double d2 = null;
        if (orders != null && orders.size() > 0) {
            for (int i = 0; i < orders.size(); i++) {
                String orderId = orders.get(i).getOrderId();
                logger.info("ZB取消订单号为:{}", orderId);
                tradeService.cancelTrade(symbol, orderId);
                sleep(1);
            }
            //5秒后卖出
            sleep(waitSecond);
            //取消订单后等待几秒钟再重新获取余额

        }
        return d2;
    }
}
