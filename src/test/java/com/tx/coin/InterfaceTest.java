package com.tx.coin;

import com.tx.coin.dto.KLineDataDTO;
import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.dto.UserInfoDTO;
import com.tx.coin.entity.Quotations;
import com.tx.coin.enums.OrderStateEnum;
import com.tx.coin.enums.TimeIntervalEnum;
import com.tx.coin.enums.TradeType;
import com.tx.coin.service.*;
import com.tx.coin.utils.JsonMapper;
import com.tx.coin.ws.api.ITradeRecordWsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;

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
    @Autowired
    private IKLineService ikLineService;

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
    public void getOrdersInfo(){
        List<OrderInfoDTO> orderInfo = orderInfoService.getBatchOrdersInfo("123969940", 1, "etc_usdt");
        System.out.println(JsonMapper.nonDefaultMapper().toJson(orderInfo));
    }

    @Test
    public void getOrderInfo(){
        //获取未完成订单
        List<OrderInfoDTO> orderInfo=orderInfoService.getOrderInfo("-1","etc_usdt");
        System.out.println(OrderStateEnum.PART_DEAL.getValue());
        for(OrderInfoDTO order:orderInfo){
            Integer state=order.getStatus();
            if (state== OrderStateEnum.PART_DEAL.getValue() || state == OrderStateEnum.UNDEAL.getValue()){
                System.out.println(order.getOrderId());
            }
        }
    }

    @Test
    public void getTradeRecordWs(){
        tradeRecordWsService.tradeRecord("etc_usdt");
    }


    @Test
    public void getKLine(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR,15);
        calendar.set(Calendar.DATE,11);
        List<KLineDataDTO> list= ikLineService.pullLineData("etc_btc", TimeIntervalEnum.ONE_HOUR,100,calendar.getTime());
        System.out.println(JsonMapper.nonDefaultMapper().toJson(list));
    }
}
