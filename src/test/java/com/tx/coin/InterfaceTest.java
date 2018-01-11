package com.tx.coin;

import com.tx.coin.dto.UserInfoDTO;
import com.tx.coin.entity.Quotations;
import com.tx.coin.service.ICoinQuotationService;
import com.tx.coin.service.ICoinTradeService;
import com.tx.coin.service.IUserInfoService;
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
    @Autowired
    private ICoinTradeService tradeService;
    @Autowired
    private IUserInfoService userInfoService;

    @Test
    public void getQuotation(){
        Quotations quotations=quotationService.getQuotation("ltc_btc");
        System.out.println(JsonMapper.nonDefaultMapper().toJson(quotations));
    }

    @Test
    public void trade(){
        tradeService.coinTrade("etc_usdt","sell",33.35,0.1);
    }

    @Test
    public void getUserInfo(){
        UserInfoDTO userInfoDTO=userInfoService.getUserInfo();
        System.out.println(JsonMapper.nonDefaultMapper().toJson(userInfoDTO));
    }

}
