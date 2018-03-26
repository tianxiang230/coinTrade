package com.tx.coin.job;

import com.tx.coin.context.PlatConfigContext;
import com.tx.coin.entity.PlatFormConfig;
import com.tx.coin.entity.Quotations;
import com.tx.coin.enums.PlatType;
import com.tx.coin.repository.PlatFormConfigRepository;
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
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.job
 * @Description
 * @date 2018-2-5 21:14
 */
@Component
public class ZbQuotationJob {
    @Autowired
    @Qualifier(value = "zbCoinQuotationServiceImpl")
    private ICoinQuotationService quotationService;
    @Autowired
    private QuotationsRepository quotationsRepository;
    @Autowired
    @Qualifier(value = "zbOperatorServiceImpl")
    private IOperatorService operatorService;
    @Autowired
    private PlatFormConfigRepository configRepository;

    Logger logger = LoggerFactory.getLogger(ZbQuotationJob.class);

    @Scheduled(cron = "0 0/5 * * * ?")
    public void execute() {
        PlatFormConfig platFormConfig = configRepository.selectByPlat(PlatType.ZB.getCode());
        if (platFormConfig == null) {
            logger.info("尚未对ZB平台做配置，执行结束");
            return;
        }
        PlatConfigContext.setCurrentConfig(platFormConfig);

        final String symbol = platFormConfig.getU1() + "_" + platFormConfig.getU2();
        Quotations quotations = quotationService.getQuotation(symbol);
        quotations.setSymbol(symbol);
        quotations.setPlat(PlatType.ZB.getCode());
        quotations.setCreateDate(DateUtil.getFormatDateTime(new Date()));
        quotations = quotationsRepository.save(quotations);
        if (quotations != null) {
            logger.info("ZB存入最新行情成功");
        }
        if (platFormConfig.getTradeOrNot()) {
            //执行交易流程
            operatorService.operate();
        }
        logger.info("执行Job完成");
    }
}
