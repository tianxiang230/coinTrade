package com.tx.coin.ws;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.ws
 * @Description websocket编码器
 * @date 2018-1-13 12:35
 */
public class SimpleEncoder implements Encoder.Text<String> {

    @Override
    public void init(EndpointConfig paramEndpointConfig) {
        //Auto-generated method stub
        System.out.println("Encoder init: " + paramEndpointConfig.getUserProperties());
    }

    @Override
    public void destroy() {
        //Auto-generated method stub
    }

    @Override
    public String encode(String paramT) throws EncodeException {
        //Auto-generated method stub
        return paramT.toUpperCase();
    }

}