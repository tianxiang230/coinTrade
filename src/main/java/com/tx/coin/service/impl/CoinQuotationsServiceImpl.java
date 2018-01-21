package com.tx.coin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.config.PropertyConfig;
import com.tx.coin.dto.QuotationsDTO;
import com.tx.coin.entity.Quotations;
import com.tx.coin.repository.QuotationsRepository;
import com.tx.coin.service.ICoinQuotationService;
import com.tx.coin.utils.HttpUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
@Service
public class CoinQuotationsServiceImpl implements ICoinQuotationService {
    @Value("${coin.remote.quota}")
    private String remoteUrl;
    @Autowired
    private QuotationsRepository quotationsRepository;
    @Autowired
    private PropertyConfig propertyConfig;
    private final int DATA_SIZE = 20;

    private Logger logger = LoggerFactory.getLogger(CoinQuotationsServiceImpl.class);

    @Override
    public Quotations getQuotation(String symbol) {
        QuotationsDTO result = null;
        String remoteStr = HttpUtil.doGetSSL(remoteUrl + symbol, null);
        logger.info("获取行情远程接口返回:{}", remoteStr);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.readValue(remoteStr, QuotationsDTO.class);
        } catch (IOException e) {
            logger.error("获取行情转换为实体发生异常,{}", ExceptionUtils.getStackTrace(e));
        }
        return result != null ? result.toEntity() : null;

    }

    @Override
    public List<Double> getLocalNewPrice(String symbol) {
        List<Double> list = quotationsRepository.getLastPriceBySymbolOrderByDate(symbol, DATA_SIZE);
        if (list.size() < DATA_SIZE) {
            propertyConfig.setTradeOrNot(false);
            throw new RuntimeException("抓取数据不足,自动关闭交易" + DATA_SIZE);
        }
        return list;
    }

    @Override
    public List<Double> getHourPrice(String symbol) {
        List<Double> list = null;
        if (Calendar.getInstance().get(Calendar.MINUTE) == 0) {
            //整点
            list = quotationsRepository.findHourBySymbolOrderByDate(symbol, DATA_SIZE);
        } else {
            list = quotationsRepository.findHourBySymbolOrderByDate(symbol, DATA_SIZE - 1);
            Double lastPrice = quotationsRepository.getLastPriceBySymbolOrderByDate(symbol, 1).get(0);
            list.add(lastPrice);
        }
        if (list.size() < DATA_SIZE) {
            propertyConfig.setTradeOrNot(false);
            throw new RuntimeException("抓取数据不足,自动关闭交易" + DATA_SIZE);
        }
        return list;
    }
}
