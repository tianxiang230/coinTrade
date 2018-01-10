package com.tx.coin.job;

import com.tx.coin.entity.Quotations;
import com.tx.coin.repository.QuotationsRepository;
import com.tx.coin.service.ICoinQuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时拉去最新价格JOB
 * Created by 你慧快乐 on 2018-1-9.
 */
@Component
public class PullQuotationJob {

    @Autowired
    private ICoinQuotationService quotationService;
    @Autowired
    private QuotationsRepository quotationsRepository;
    private Logger logger = LoggerFactory.getLogger(PullQuotationJob.class);
    private final String symbol = "ltc_btc";

//    @Scheduled(cron = "0 0/1 * * * ?")
    @Scheduled(cron = "0 0/15 * * * ?")
    public void execute() {
        Quotations quotations = quotationService.getQuotation(symbol);
        quotations.setSymbol(symbol);
        quotations.setCreateDate(new Date());
        quotations = quotationsRepository.save(quotations);
        if (quotations != null) {
            logger.info("存入最新行情成功");
        }
    }

}
