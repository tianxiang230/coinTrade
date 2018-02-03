package com.tx.coin;

import com.alibaba.fastjson.JSON;
import com.tx.coin.dto.UserInfoDTO;
import com.tx.coin.enums.TradeType;
import com.tx.coin.service.ICoinTradeService;
import com.tx.coin.service.IUserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin
 * @Description
 * @date 2018-1-31 20:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BinIntTest {
    @Autowired
    @Qualifier(value = "binUserInfoServiceImpl")
    private IUserInfoService userInfoService;
    @Autowired
    @Qualifier(value = "binCoinTradeServiceImpl")
    private ICoinTradeService tradeService;

    @Test
    public void testUserInfo() {
        Map<String,Object> userInfoDTO = userInfoService.getUserInfo();
        System.out.println(JSON.toJSONString(userInfoDTO));
    }


    @Test
    public void testBuy() {
        String symbol = "INSBTC";
        double price = 0.0002;
        double amount = 10;
        String orderId = tradeService.coinTrade(symbol, TradeType.BUY, price, amount);
        System.out.println(orderId);
    }

    @Test
    public void cancelOrder() {
        String symbol = "INSBTC";
        String orderId = "1260764";
        boolean result = tradeService.cancelTrade(symbol, orderId);
        System.out.println(result);
    }
}
