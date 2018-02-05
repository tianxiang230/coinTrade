package com.tx.coin;

import com.tx.coin.entity.Quotations;
import com.tx.coin.service.ICoinQuotationService;
import com.tx.coin.utils.JsonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin
 * @Description
 * @date 2018-2-5 16:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZbIntTest {
    @Autowired
    @Qualifier(value = "zbCoinQuotationServiceImpl")
    private ICoinQuotationService quotationService;

    @Test
    public void testQuotation() {
        String symbol = "btc_udt";
        Quotations quotation = quotationService.getQuotation(symbol);
        System.out.println(JsonMapper.nonDefaultMapper().toJson(quotation));
    }
}
