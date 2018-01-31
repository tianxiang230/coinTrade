package com.tx.coin;

import com.alibaba.fastjson.JSON;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.domain.market.TickerStatistics;
import org.junit.Test;

import java.util.List;

import static com.binance.api.client.domain.account.NewOrder.marketBuy;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin
 * @Description
 * @date 2018-1-30 19:48
 */

public class BinanceIntTest {
    String appKey = "hxUARdjucDDe2UNswXpo1ywbw1p4Cpm7zlDfCxiPWySho5sMTQ7oAU5xlmNNU3sA";
    String secret = "ktJ598k7YdZbUGfZjq9QoBtWbfyHqgJUxkEllwkf6opkCbZneaAv2m62fBtzb35u";

    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(appKey, secret);
    BinanceApiRestClient client = factory.newRestClient();

    @Test
    public void ping() {

        client.ping();
    }

    @Test
    public void serverTime() {
        long serverTime = client.getServerTime();
        System.out.println(serverTime);
    }

    @Test
    public void account() {
        Account account = client.getAccount();
        System.out.println(JSON.toJSONString(account));
        System.out.println(account.getBalances());
        System.out.println(account.getAssetBalance("ETH").getFree());

    }

    @Test
    public void cancelOrder() {
        client.cancelOrder(new CancelOrderRequest("LINKETH", 123015L));
    }

    @Test
    public void trade() {
        NewOrderResponse newOrderResponse = client.newOrder(marketBuy("LINKETH", "1000"));
        System.out.println(newOrderResponse.getClientOrderId());
    }

    @Test
    public void testPrice() {
        TickerStatistics statistics = client.get24HrPriceStatistics("INSBTC");
        String lastPrice = statistics.getLastPrice();
        System.out.println(JSON.toJSONString(statistics));
    }

    @Test
    public void hisOrders() {
//        AllOrdersRequest request=new AllOrdersRequest("INSBTC");
//        List<Order> orders=client.getAllOrders(request);
//        System.out.println(JSON.toJSONString(orders));
        OrderRequest orderRequest = new OrderRequest("INSBTC");
        List<Order> openOrders = client.getOpenOrders(orderRequest);
        System.out.println(JSON.toJSONString(openOrders));
    }
}
