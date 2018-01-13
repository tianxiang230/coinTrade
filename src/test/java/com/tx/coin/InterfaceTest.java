package com.tx.coin;

import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.dto.UserInfoDTO;
import com.tx.coin.entity.Quotations;
import com.tx.coin.enums.TradeType;
import com.tx.coin.service.ICoinQuotationService;
import com.tx.coin.service.ICoinTradeService;
import com.tx.coin.service.IOrderInfoService;
import com.tx.coin.service.IUserInfoService;
import com.tx.coin.utils.JsonMapper;
import com.tx.coin.ws.api.ITradeRecordWsService;
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
    @Autowired
    private IOrderInfoService orderInfoService;
    @Autowired
    private ITradeRecordWsService tradeRecordWsService;

    @Test
    public void getQuotation(){
        Quotations quotations=quotationService.getQuotation("ltc_btc");
        System.out.println(JsonMapper.nonDefaultMapper().toJson(quotations));
    }

    /**
     * {"result":true,"order_id":123969940}
     */
    @Test
    public void trade(){
        tradeService.coinTrade("etc_usdt", TradeType.SELL,33.05,0.1);
    }

    @Test
    public void getUserInfo(){
        UserInfoDTO userInfoDTO=userInfoService.getUserInfo();
        System.out.println(JsonMapper.nonDefaultMapper().toJson(userInfoDTO));
    }

    @Test
    public void getOrderInfo(){
        OrderInfoDTO orderInfo = orderInfoService.getOrderInfo("123969940", 1, "etc_usdt");
        System.out.println(JsonMapper.nonDefaultMapper().toJson(orderInfo));
    }

    @Test
    public void getTradeRecordWs(){
        tradeRecordWsService.tradeRecord("etc_usdt");
    }
}
