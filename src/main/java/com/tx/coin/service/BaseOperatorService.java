package com.tx.coin.service;

import com.tx.coin.context.PlatConfigContext;
import com.tx.coin.entity.PlatFormConfig;
import com.tx.coin.enums.TradeType;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service
 * @Description
 * @date 2018-2-5 20:37
 */
public abstract class BaseOperatorService {
    protected abstract ICoinTradeService getTradeService();

    protected void buy(String symbol, Double currentPrice, Double lb) {
        PlatFormConfig zbPropertyConfig = PlatConfigContext.getCurrentConfig();
        if (currentPrice != null && lb != null && symbol != null) {
            if (currentPrice > lb) {
                //在LB价格位置买入S1手
                getTradeService().coinTrade(symbol, TradeType.BUY, lb, zbPropertyConfig.getS1());
            } else {
                double buyPrice = currentPrice * zbPropertyConfig.getB1();
                getTradeService().coinTrade(symbol, TradeType.BUY, buyPrice, zbPropertyConfig.getS1());
            }
        }
    }

    protected void sleep(int sleepSecond) {
        try {
            Thread.sleep(sleepSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
