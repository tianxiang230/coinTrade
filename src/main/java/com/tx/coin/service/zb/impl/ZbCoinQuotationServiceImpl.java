package com.tx.coin.service.zb.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.entity.Quotations;
import com.tx.coin.service.ICoinQuotationService;
import com.tx.coin.utils.DateUtil;
import com.tx.coin.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.zb.impl
 * @Description ZB获取行情接口实现
 * @date 2018-2-5 15:05
 */
@Service
public class ZbCoinQuotationServiceImpl implements ICoinQuotationService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private String remoteUrl = "http://api.bitkk.com/data/v1/ticker";
    private Logger logger = LoggerFactory.getLogger(ZbCoinQuotationServiceImpl.class);

    @Override
    public Quotations getQuotation(String symbol) {
        String result = HttpUtil.getInstance().requestHttpGet(remoteUrl + "", "market=" + symbol);
        logger.info("ZB获取行情远程接口返回:{}", result);
        Quotations quotations = null;
        try {
            JsonNode jsonNode = objectMapper.readTree(result);
            if (jsonNode.get("error") != null) {
                logger.info("ZB获取行情出错,{}", jsonNode.get("error").asText());
                return quotations;
            }
            JsonNode tickerNode = jsonNode.get("ticker");
            quotations = new Quotations();
            quotations.setSymbol(symbol);
            quotations.setBuy(tickerNode.get("buy").asDouble());
            quotations.setSell(tickerNode.get("sell").asDouble());
            quotations.setVol(tickerNode.get("vol").asDouble());
            quotations.setHigh(tickerNode.get("high").asDouble());
            quotations.setLow(tickerNode.get("low").asDouble());
            quotations.setLast(tickerNode.get("last").asDouble());
            quotations.setDate(DateUtil.getFormatDateTime(jsonNode.get("date").asLong()));
            quotations.setCreateDate(quotations.getDate());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quotations;
    }
}
