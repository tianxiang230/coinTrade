package com.tx.coin.service.common.impl;

import com.tx.coin.enums.PlatType;
import com.tx.coin.repository.QuotationsRepository;
import com.tx.coin.service.common.IQuotationCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service
 * @Description
 * @date 2018-1-31 18:02
 */
@Component
public class QuotationCommonServiceImpl implements IQuotationCommonService {
    @Autowired
    private QuotationsRepository quotationsRepository;

    @Override
    public List<Double> getLocalNewPrice(String symbol, PlatType platType) {
        List<Double> list = quotationsRepository.getLastPriceBySymbolOrderByDate(symbol, platType.getCode(), DATA_SIZE);
        return list;
    }

    @Override
    public List<Double> getHourPrice(String symbol, PlatType platType) {
        List<Double> list = null;
        if (Calendar.getInstance().get(Calendar.MINUTE) == 0) {
            //整点
            list = quotationsRepository.findHourBySymbolOrderByDate(symbol, platType.getCode(), DATA_SIZE);
        } else {
            list = quotationsRepository.findHourBySymbolOrderByDate(symbol, platType.getCode(), DATA_SIZE - 1);
            Double lastPrice = quotationsRepository.getLastPriceBySymbolOrderByDate(symbol, platType.getCode(), 1).get(0);
            list.add(lastPrice);
        }
        return list;
    }
}
