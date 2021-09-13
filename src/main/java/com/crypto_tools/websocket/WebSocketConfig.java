package com.crypto_tools.websocket;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.net.URI;


@Slf4j
@Component
public class WebSocketConfig {

    /**
     * ServerEndpointExporter 作用
     * このBeanは自動敵に@ServerEndpointで注解されたwebsocket endpointを登録する
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    @Bean
    public WebSocketClient webSocketClient() {
        try {
            WebSocketClient webSocketClient = new WebSocketClient(new URI("wss://wsaws.okex.com:8443/ws/v5/public"),new Draft_6455()) {

                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    log.info("[websocket] 连接成功");
                }


                @Override
                public void onMessage(String message) {
                    log.info("[websocket] 收到消息={}",message);

                }


                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.info("[websocket] 退出连接");
                }


                @Override
                public void onError(Exception ex) {
                    log.info("[websocket] 连接错误={}",ex.getMessage());
                }
            };

            webSocketClient.connect();
            return webSocketClient;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

}
