package com.crypto_tools.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *まずServerEndpointExporterクラスを登録する、このBeanは自動に@ServerEndpointにアノテーションされてるクラスを登録する
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {return new ServerEndpointExporter();}
}
