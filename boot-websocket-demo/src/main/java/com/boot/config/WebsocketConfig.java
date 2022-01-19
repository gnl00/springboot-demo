package com.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebsocketConfig
 *
 * @author lgn
 * @since 2021/12/22 18:44
 */

@Configuration
public class WebsocketConfig {

    /**
     * 检测所有带有 @ServerEndpoint 注解的 bean 并注册到容器中
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
