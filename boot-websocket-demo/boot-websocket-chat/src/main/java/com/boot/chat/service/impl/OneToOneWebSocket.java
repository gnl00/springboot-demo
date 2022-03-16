package com.boot.chat.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;

/**
 * OneToOneWebSocket 一对一发消息
 *
 * @author lgn
 * @since 2022/3/16 15:34
 */

@Slf4j
@Service
@ServerEndpoint(value = "/chat/oneToOne")
public class OneToOneWebSocket {
}
