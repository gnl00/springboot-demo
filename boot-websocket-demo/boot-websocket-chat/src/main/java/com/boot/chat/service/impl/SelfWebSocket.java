package com.boot.chat.service.impl;

import com.boot.chat.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.UUID;

/**
 * SelfWebSocket 自己给自己发消息
 *
 * @author lgn
 * @since 2022/3/16 15:32
 */

@Slf4j
@ServerEndpoint(value = "/chat/self")
@Service
public class SelfWebSocket implements WebSocketService {

    /**
     * 当前在线人数
     */
    private static volatile Integer onCount = 0;

    @OnOpen
    public void onOpen() {
        onCount = onCount + 1;
        log.info("有链接加入， 当前在线人数: {}", onCount);
    }

    @OnClose
    public void onClose() {
        onCount = onCount - 1;
        log.info("有链接关闭， 当前在线人数: {}", onCount);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到 {} 的消息: {}", session.getId(), message);

        String sendMsg = UUID.randomUUID().toString();
        sendMessage(sendMsg, session);
    }

    @OnError
    public void onError(Session session, Throwable e) {
        log.error("链接 {} 出错啦~ {}", session.getId(), e.getCause().getMessage());
    }

    @Override
    public void sendMessage(String message, Session to) {
        log.info("发送消息: {} 给 {}", message, to.getId());

        try {
            to.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
