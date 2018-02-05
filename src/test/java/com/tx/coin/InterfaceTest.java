package com.tx.coin;

import com.tx.coin.dto.KLineDataDTO;
import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.dto.UserInfoDTO;
import com.tx.coin.entity.Quotations;
import com.tx.coin.enums.OrderStateEnum;
import com.tx.coin.enums.TimeIntervalEnum;
import com.tx.coin.enums.TradeType;
import com.tx.coin.service.ICoinQuotationService;
import com.tx.coin.service.ICoinTradeService;
import com.tx.coin.service.IOrderInfoService;
import com.tx.coin.service.IUserInfoService;
import com.tx.coin.service.okxe.*;
import com.tx.coin.service.okxe.impl.OkxeOperatorServiceServiceImpl;
import com.tx.coin.utils.JsonMapper;
import com.tx.coin.ws.api.ITradeRecordWsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 测试外部接口
 * Created by 你慧快乐 on 2018-1-9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InterfaceTest {

    @Autowired
    @Qualifier(value = "okxeCoinQuotationsServiceImpl")
    private ICoinQuotationService quotationService;
    @Autowired
    @Qualifier(value = "okxeCoinTradeServiceImpl")
    private ICoinTradeService tradeService;
    @Autowired
    @Qualifier(value = "okxeUserInfoServiceImpl")
    private IUserInfoService userInfoService;
    @Autowired
    @Qualifier(value = "okxeOrderInfoServiceImpl")
    private IOrderInfoService orderInfoService;


    @Test
    public void getQuotation() {
        Quotations quotations = quotationService.getQuotation("ltc_btc");
        System.out.println(JsonMapper.nonDefaultMapper().toJson(quotations));
    }

    /**
     * {"result":true,"order_id":123969940}
     */
    @Test
    public void trade() {
        tradeService.coinTrade("etc_usdt", TradeType.SELL, 33.05, 0.1);
    }

    @Test
    public void getUserInfo() {
        Map<String,Object> userInfoDTO = userInfoService.getUserInfo();
        System.out.println(JsonMapper.nonDefaultMapper().toJson(userInfoDTO));
    }


    @Test
    public void getOrderInfo() {
        //获取未完成订单
        List<OrderInfoDTO> orderInfo = orderInfoService.getOpenOrderInfo("-1", "etc_usdt");
        System.out.println(OrderStateEnum.PART_DEAL.getValue());
        for (OrderInfoDTO order : orderInfo) {
            Integer state = order.getStatus();
            if (state == OrderStateEnum.PART_DEAL.getValue() || state == OrderStateEnum.UNDEAL.getValue()) {
                System.out.println(order.getOrderId());
            }
        }
    }

}
