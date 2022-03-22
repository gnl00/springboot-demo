package com.boot.chat.service.impl;

import com.boot.chat.bean.WSMessage;
import com.boot.chat.util.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    private static Map<String, Session> onLineUser = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        onLineUser.put(session.getId(), session);
        log.info("ToOne 有链接 {} 加入， 当前在线人数: {}", session.getId(), onLineUser.size());

        sendInitialMsg(session);
        sendUpdateMsg(session);
    }

    @OnClose
    public void onClose(Session session) {
        onLineUser.remove(session.getId());
        log.info("ToOne 有链接 {} 关闭， 当前在线人数: {}", session.getId(), onLineUser.size());

        sendUpdateMsg(session);
    }

    @OnError
    public void onError(Session session, Throwable e) {
        log.error("ToOne 有链接 {} 出错啦~ {}", session.getId(), e.getCause().getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("ToOne 收到 {} 的消息: {}", session.getId(), message);
        try {

            // 收到消息，格式转为 WSMessage
            WSMessage msgObj = JacksonUtils.readValue(message);
            log.info("ToOne json to obj: {}", msgObj.toString());

            // 消息分发给对应的接收方
            String toId = msgObj.getTo();

            if ("websocket-server".equals(toId)) {
                String msgBody = UUID.randomUUID().toString();

                WSMessage<String> msg = new WSMessage<>();
                msg.setFrom(toId);
                msg.setTo(session.getId());
                msg.setBody(msgBody);
                msg.setBodyType("text");
                msg.setMsgType("contact");

                String finalMsg = JacksonUtils.writeObjectAsString(msg);
                TimeUnit.SECONDS.sleep(2);

                log.info("Self 发送消息: {} 给 {}", finalMsg, session.getId());
                sendMessage(finalMsg, session);

            } else {
                Session toSession = onLineUser.get(toId);
                messageDelivery(message, toSession);
            }

        } catch (JsonProcessingException e) {
            log.error("convert json to object fail, {}", e.getCause().getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化消息，发送 Id 与当前在线用户
     */
    public void sendInitialMsg(Session session) {
        log.info("sendInitialMsg...");

        Map<String, Object> msgMap = new HashMap<>(2);
        msgMap.put("uid", session.getId());
        msgMap.put("msgType", "init");
        msgMap.put("online", onLineUser.keySet().stream().filter(uid -> uid != session.getId()).collect(Collectors.toList()));

        try {
            String jsonStr = JacksonUtils.writeObjectAsString(msgMap);
            sendMessage(jsonStr, session);

        } catch (JsonProcessingException e) {
            log.error("convert map to json fail, {}", e.getCause().getMessage());
            e.printStackTrace();
        }

    }

    public void sendUpdateMsg(Session current) {

        if (count() <= 1) {
            return;
        }

        log.info("sendUpdateMsg...");

        // 更新在线用户数据
        onLineUser.keySet().stream()
                .filter(sessionId -> !sessionId.equals(current.getId()))
                .forEach(sessionId -> {
                    Map<String, Object> msgMap = new HashMap<>();
                    msgMap.put("msgType", "update");
                    msgMap.put("online", onLineUser.keySet().stream().filter(onlineId -> !onlineId.equals(sessionId)).collect(Collectors.toSet()));

                    try {
                        String jsonStr = JacksonUtils.writeObjectAsString(msgMap);
                        sendMessage(jsonStr, onLineUser.get(sessionId));

                    } catch (JsonProcessingException e) {
                        log.error("convert map to json fail, {}", e.getCause().getMessage());
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 消息发送
     */
    public void sendMessage(String message, Session session) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息分发
     */
    public void messageDelivery(String message, Session session) {
        log.info("ToOne 消息分发 {} 给 {}", message, session.getId());
        sendMessage(message, session);
    }

    /**
     * 统计在线用户
     */
    public int count() {
        return onLineUser.size();
    }

}
