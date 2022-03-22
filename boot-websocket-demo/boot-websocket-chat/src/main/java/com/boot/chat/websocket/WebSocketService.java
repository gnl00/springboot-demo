package com.boot.chat.websocket;

import com.boot.chat.cache.SessionStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocketService
 *
 * @author lgn
 * @since 2022/3/22 10:08
 */

@Slf4j
@Service
@ServerEndpoint(value = "/chat")
public class WebSocketService {

    @OnOpen
    public void onOpen(Session session) {
        SessionStore.add(session.getId(), session);

        // throw new RuntimeException("play some tricks");
    }

    @OnClose
    public void onClose(Session session) {
        SessionStore.remove(session.getId());
        log.info("closing session: {}", session.getId());
    }

    @OnError
    public void onError(Session session, Throwable e) {
        log.error("WebSocketService 有链接 {} 出错啦~ {}", session.getId(), e.getCause().getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session) {}

}
