package com.tx.coin.ws;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.ws
 * @Description websocket解码实现
 * @date 2018-1-13 12:34
 */
public class SampleDecoder implements Decoder.Text<String>{

    @Override
    public void init(EndpointConfig paramEndpointConfig) {
        // Auto-generated method stub
    }

    @Override
    public void destroy() {
        // Auto-generated method stub
    }

    @Override
    public String decode(String paramString) throws DecodeException {
        // Auto-generated method stub
        return paramString.toLowerCase();
    }

    @Override
    public boolean willDecode(String paramString) {
        // Auto-generated method stub
        return true;
    }

}
