package com.tx.coin.job;

import com.tx.coin.config.OkxePropertyConfig;
import com.tx.coin.entity.Quotations;
import com.tx.coin.repository.QuotationsRepository;
import com.tx.coin.service.ICoinQuotationService;
import com.tx.coin.service.IOperatorService;
import com.tx.coin.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时拉去最新价格JOB
 * Created by 你慧快乐 on 2018-1-9.
 */
@Component
public class PullOkxeQuotationJob {

    @Autowired
    @Qualifier(value = "okxeCoinQuotationsServiceImpl")
    private ICoinQuotationService quotationService;
    @Autowired
    private QuotationsRepository quotationsRepository;
    @Autowired
    @Qualifier(value = "okxeOperatorServiceImpl")
    private IOperatorService operatorService;
    @Autowired
    private OkxePropertyConfig okxePropertyConfig;
    private Logger logger = LoggerFactory.getLogger(PullOkxeQuotationJob.class);

    //    @Scheduled(cron = "0 0/5 * * * ?")
    @Scheduled(cron = "0 0/15 * * * ?")
    public void execute() {
        final String symbol = okxePropertyConfig.getU1() + "_" + okxePropertyConfig.getU2();
        Quotations quotations = quotationService.getQuotation(symbol);
        quotations.setSymbol(symbol);
        quotations.setCreateDate(DateUtil.getFormatDateTime(new Date()));
        quotations = quotationsRepository.save(quotations);
        if (quotations != null) {
            logger.info("存入最新行情成功");
        }
        if (okxePropertyConfig.getTradeOrNot()) {
            //执行交易流程
            operatorService.operate();
        }
        logger.info("执行Job完成");
    }
}
