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
 * @date 2018-2-1 15:54
 */
@Component
public class BinQuotationJob {
    @Autowired
    @Qualifier(value = "binCoinQuotationServiceImpl")
    private ICoinQuotationService quotationService;
    @Autowired
    private QuotationsRepository quotationsRepository;
    @Autowired
    private PlatFormConfigRepository configRepository;
    @Autowired
    @Qualifier(value = "binOperatorServiceServiceImpl")
    private IOperatorService operatorService;

    private Logger logger = LoggerFactory.getLogger(BinQuotationJob.class);

    @Scheduled(cron = "0 0/2 * * * ?")
    public void execute() {
        PlatFormConfig platFormConfig = configRepository.selectByPlat(PlatType.BIN.getCode());
        if (platFormConfig == null) {
            logger.info("尚未对币安平台做配置，执行结束");
            return;
        }
        PlatConfigContext.setCurrentConfig(platFormConfig);

        final String symbol = platFormConfig.getU1() + platFormConfig.getU2();
        Quotations quotations = quotationService.getQuotation(symbol);
        quotations.setSymbol(symbol);
        quotations.setDate(quotations.getCreateDate());
        quotations.setPlat(PlatType.BIN.getCode());
        quotations.setCreateDate(DateUtil.getFormatDateTime(new Date()));
        quotations = quotationsRepository.save(quotations);
        if (quotations != null) {
            logger.info("币安存入最新行情成功");
        }
        if (platFormConfig.getTradeOrNot()) {
            //执行交易流程
            operatorService.operate();
        }
        logger.info("执行Job完成");
    }
}
