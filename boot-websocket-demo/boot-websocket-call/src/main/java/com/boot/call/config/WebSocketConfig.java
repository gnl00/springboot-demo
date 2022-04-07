package com.boot.call.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocketConfig
 *
 * @author lgn
 * @since 2022/4/7 10:09
 */

@Configuration
public class WebSocketConfig {
    /**
     * 检测所有带有 @ServerEndpoint 注解的 bean 并注册到容器中
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
