package com.tx.coin.ws.api.impl;

import com.tx.coin.config.PropertyConfig;
import com.tx.coin.utils.EncryptHelper;
import com.tx.coin.ws.WebSocketClient;
import com.tx.coin.ws.api.ITradeRecordWsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.ws.api
 * @Description
 * @date 2018-1-13 12:59
 */
@Service
public class TradeRecordServiceImpl implements ITradeRecordWsService {
    @Autowired
    private PropertyConfig propertyConfig;
    Logger logger = LoggerFactory.getLogger(TradeRecordServiceImpl.class);

    @Override
    public String tradeRecord(String symbol) {
        WebSocketContainer conmtainer = ContainerProvider.getWebSocketContainer();
        WebSocketClient client = new WebSocketClient();
        try {
            conmtainer.connectToServer(client,
                    new URI("wss://real.okex.com:10441/websocket"));
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Map<String, String> param = new HashMap<>(6);
        param.put("api_key", propertyConfig.getApiKey());
        String sign = EncryptHelper.sign(param, propertyConfig.getSecretKey(), "utf-8");
        param.put("sign", sign);

        StringBuilder params = new StringBuilder("{'event':'addChannel','channel':'ok_sub_spot_");
        params.append(symbol);
        params.append("_deals','parameters':{'api_key':'");
        params.append(propertyConfig.getApiKey()).append("','sign':'");
        params.append(sign).append("'}}");
        logger.info("订阅交易行情发送数据:{}", params.toString());
        client.send(params.toString());

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
