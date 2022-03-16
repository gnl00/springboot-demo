package com.boot.chat.service.impl;

import com.boot.chat.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * OneToManyWebSocket 一对多发消息
 *
 * @author lgn
 * @since 2022/3/16 15:33
 */

@Slf4j
@Service
@ServerEndpoint(value = "/chat/oneToMany")
public class OneToManyWebSocket implements WebSocketService {

    @Override
    public void sendMessage(String message, Session to) {

    }

}
