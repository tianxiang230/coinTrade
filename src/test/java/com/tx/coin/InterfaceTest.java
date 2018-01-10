package com.tx.coin;

import com.tx.coin.entity.Quotations;
import com.tx.coin.service.ICoinQuotationService;
import com.tx.coin.utils.JsonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试外部接口
 * Created by 你慧快乐 on 2018-1-9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InterfaceTest {

    @Autowired
    private ICoinQuotationService quotationService;

    @Test
    public void getQuotation(){
        Quotations quotations=quotationService.getQuotation("ltc_btc");
        System.out.println(JsonMapper.nonDefaultMapper().toJson(quotations));
    }
}
