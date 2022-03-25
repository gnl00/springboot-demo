package com.boot.chat.service.impl;

import com.boot.chat.bean.WSMessage;
import com.boot.chat.service.WebSocketService;
import com.boot.chat.util.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private static Map<String, Session> userMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        userMap.put(session.getId(), session);

        log.info("ToMany 有链接 {} 加入， 当前在线人数: {}", session.getId(), userMap.size());
    }

    @OnClose
    public void onClose(Session session) {
        userMap.remove(session.getId());

        log.info("ToMany 有链接 {} 加入， 当前在线人数: {}", session.getId(), userMap.size());
    }

    @OnError
    public void onError(Session session, Throwable e) {
        log.error("ToMany 有链接 {} 出错啦~ {}", session.getId(), e.getCause().getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("ToMany 收到 {} 的消息: {}", session.getId(), message);

        WSMessage msg = JacksonUtils.readValue(message);

        WSMessage objMsg = WSMessage.builder().from(session.getId()).to(msg.getTo()).body(msg.getBody()).date(msg.getDate()).build();

        String jsonMsg = JacksonUtils.writeObjectAsString(objMsg);

        userMap.keySet().stream().filter(toId -> !toId.equals(session.getId())).forEach(member -> {
            log.info("ToMany 发送 {} 给 {}", jsonMsg, member);
            messageDelivery(jsonMsg, userMap.get(member));
        });

    }

    public void messageDelivery(String message, Session session) {
        sendMessage(message, session);
    }

    @Override
    public void sendMessage(String message, Session to) {
        try {
            to.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
