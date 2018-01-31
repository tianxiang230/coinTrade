package com.tx.coin.service.okxe.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.dto.QuotationsDTO;
import com.tx.coin.entity.Quotations;
import com.tx.coin.service.ICoinQuotationService;
import com.tx.coin.utils.HttpUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
@Service
public class OkxeCoinQuotationsServiceImpl implements ICoinQuotationService {
    @Value("${coin.remote.quota}")
    private String remoteUrl;

    private Logger logger = LoggerFactory.getLogger(OkxeCoinQuotationsServiceImpl.class);

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

}
