package com.tx.coin.service.binance.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerStatistics;
import com.tx.coin.entity.Quotations;
import com.tx.coin.service.ICoinQuotationService;
import com.tx.coin.utils.DateUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.binance.impl
 * @Description 币安获取最新价格实现
 * @date 2018-1-30 21:40
 */
@Component
public class BinCoinQuotationServiceImpl implements ICoinQuotationService {
    @Autowired
    private BinanceApiRestClient restClient;
    private Logger logger = LoggerFactory.getLogger(BinCoinTradeServiceImpl.class);

    @Override
    public Quotations getQuotation(String symbol) {
        TickerStatistics statistics = null;
        try {
            statistics = restClient.get24HrPriceStatistics(symbol);
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return null;
        }

        Quotations quotations = convertToQuotation(statistics);
        quotations.setSymbol(symbol);
        return quotations;
    }

    private Quotations convertToQuotation(TickerStatistics statistics) {
        Date now = new Date();
        Quotations quotations = new Quotations();
        quotations.setLast(Double.valueOf(statistics.getLastPrice()));
        quotations.setVol(Double.valueOf(statistics.getVolume()));
        quotations.setSell(Double.valueOf(statistics.getWeightedAvgPrice()));
        quotations.setHigh(Double.valueOf(statistics.getHighPrice()));
        quotations.setLow(Double.valueOf(statistics.getLowPrice()));
        quotations.setBuy(Double.valueOf(statistics.getOpenPrice()));
        quotations.setCreateDate(DateUtil.getFormatDateTime(now));
        return quotations;
    }

}
