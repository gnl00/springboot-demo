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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    private static String from = "websocket-server";
    private static String receiver = "";

    @OnOpen
    public void onOpen(Session session) {
        onCount = onCount + 1;
        log.info("Self 有链接 {} 加入， 当前在线人数: {}", session.getId(), onCount);
    }

    @OnClose
    public void onClose(Session session) {
        onCount = onCount - 1;
        log.info("Self 有链接 {} 关闭， 当前在线人数: {}", session.getId(), onCount);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("Self 收到 {} 的消息: {}", session.getId(), message);
        receiver = session.getId();

        String sendMsg = UUID.randomUUID().toString();
        sendMessage(sendMsg, session);
    }

    @OnError
    public void onError(Session session, Throwable e) {
        log.error("Self 有链接 {} 出错啦~ {}", session.getId(), e.getCause().getMessage());
    }

    @Override
    public void sendMessage(String body, Session to) {

        WSMessage<String> msg = new WSMessage<>();
        msg.setFrom(from);
        msg.setTo(receiver);
        msg.setBody(body);
        msg.setBodyType("text");
        msg.setMsgType("contact");

        try {
            String finalMsg = JacksonUtils.writeObjectAsString(msg);

            TimeUnit.SECONDS.sleep(2);

            log.info("Self 发送消息: {} 给 {}", finalMsg, to.getId());

            to.getBasicRemote().sendText(finalMsg);
            // to.getBasicRemote().sendObject(null);
        } catch (JsonProcessingException e) {
            log.error("convert object to json string fail, {}", e.getCause().getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
