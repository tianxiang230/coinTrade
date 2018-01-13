package com.tx.coin.ws;

import com.tx.coin.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.HandshakeResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.ws
 * @Description
 * @date 2018-1-13 12:37
 */
public class SampleConfigurator extends ClientEndpointConfig.Configurator {
    private Logger logger= LoggerFactory.getLogger(SampleConfigurator.class);

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {
        //Auto-generated method stub
        logger.info("发送数据前head:{}", JsonMapper.nonDefaultMapper().toJson(headers));
    }

    @Override
    public void afterResponse(HandshakeResponse handshakeResponse) {
        //Auto-generated method stub
        logger.info("响应数据后:{}",JsonMapper.nonDefaultMapper().toJson(handshakeResponse));
    }

}