package com.tx.coin;

import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.entity.Quotations;
import com.tx.coin.enums.TradeType;
import com.tx.coin.service.*;
import com.tx.coin.utils.JsonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Autowired
    @Qualifier(value = "zbSymbolServiceImpl")
    private ISymbolService symbolService;
    @Autowired
    @Qualifier(value = "zbCoinUserInfoServiceImpl")
    private IUserInfoService userInfoService;
    @Autowired
    @Qualifier(value = "zbCoinTradeServiceImpl")
    private ICoinTradeService tradeService;

    @Autowired
    @Qualifier(value = "zbOrderInfoServiceImpl")
    private IOrderInfoService orderInfoService;
    String symbol = "usdt_qc";

    @Test
    public void testQuotation() {
        Quotations quotation = quotationService.getQuotation(symbol);
        System.out.println(JsonMapper.nonDefaultMapper().toJson(quotation));
    }

    @Test
    public void testSymbol() {
        Map<String, Set<String>> symbolPairs = symbolService.getSymbolPairs();
        System.out.println(JsonMapper.nonDefaultMapper().toJson(symbolPairs));
    }

    @Test
    public void testUserInfo() {
        Map<String, Object> userInfo = userInfoService.getUserInfo();
        System.out.println(JsonMapper.nonDefaultMapper().toJson(userInfo));
    }

    @Test
    public void buy() {
        double price = 6;
        double amount = 10;
        String orderId = tradeService.coinTrade(symbol, TradeType.BUY, price, amount);
        System.out.println(orderId);
    }

    @Test
    public void openedOrder() {
        List<OrderInfoDTO> openOrderInfo = orderInfoService.getOpenOrderInfo(null, symbol);
        System.out.println(JsonMapper.nonDefaultMapper().toJson(openOrderInfo));
    }

    @Test
    public void cancelOrder() {
        String orderId = "20180207553050";
        boolean result = tradeService.cancelTrade(symbol, orderId);
        System.out.println(result);
    }
}
