package com.tx.coin.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.ws
 * @Description websocket客户端
 * @date 2018-1-13 12:31
 */
@ClientEndpoint(configurator=SampleConfigurator.class,
        decoders={SampleDecoder.class},
        encoders={SimpleEncoder.class},
        subprotocols={"subprotocol1"})
public class WebSocketClient {

    private Logger logger = LoggerFactory.getLogger(WebSocketClient.class);
    private Session session;

    @OnOpen
    public void open(Session session){
        logger.info("Client WebSocket is opening...");
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message){
        TradeRecordAdapter.updateRecordDetails(message);
        logger.info("Server return message: " + message);
    }

    @OnClose
    public void onClose(){
        logger.info("Websocket closed");
    }


    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
    }

    public void send(String message){
        this.session.getAsyncRemote().sendText(message);
    }

    public void close() throws IOException {
        if(this.session.isOpen()){
            this.session.close();
        }
    }
}
