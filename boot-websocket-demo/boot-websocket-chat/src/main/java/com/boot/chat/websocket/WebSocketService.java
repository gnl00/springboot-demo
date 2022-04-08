package com.boot.chat.websocket;

import com.boot.chat.bean.CacheDo;
import com.boot.chat.bean.OnlineDo;
import com.boot.chat.bean.WSMessage;
import com.boot.chat.cache.SessionCache;
import com.boot.chat.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * WebSocketService
 *
 * @author lgn
 * @since 2022/3/22 10:08
 */

@Slf4j
@Service
@ServerEndpoint(value = "/chat/{uid}")
public class WebSocketService {

    /**
     * key = uid
     * value = sessionId
     */
    public static Map<String, OnlineDo> onlineMap = new ConcurrentHashMap<>();

    /**
     * key = uid,
     * value = message lists which have not received
     */
    public static Map<String, List<String>> messageCache = new ConcurrentHashMap<>();

    /**
     * key = groupId,
     * value = groupMember list
     */
    public static Map<String, Set<String>> groups = new ConcurrentHashMap<>();

    /**
     * @Autowired（依赖注入） 在 SpringBoot 与 WebSocket 集成中存在一个坑，因为 Spring 容器中的 Bean 默认是单例的，
     * 而 WebSocket 每开启一次连接就会创建一个对象，是多实例的，就会出现注入的依赖为 null 的情况
     *
     * 此时只需要将注入的依赖设置为成员变量，然后再赋值即可
     *
     * 参考：
     * https://blog.csdn.net/jingyoushui/article/details/111318704
     * https://www.itdaan.com/blog/2019/07/05/6f564e1bbb9c8e2a926dcf59bf9a3841.html
     */

    /**
     * key = sessionId
     * value = CacheDo
     */
    private static SessionCache cache;

    @Autowired
    public void setStore(SessionCache cache) {
        WebSocketService.cache = cache;
    }

    private String getUid(String sessionId) {
        CacheDo cacheDo = cache.get(sessionId);
        return cacheDo.getUid();
    }

    /**
     * 获取 ws 连接路径携带的参数
     * @ServerEndpoint(value = "/chat/{param}")
     * onOpen(Session session, @PathParam("param") String param)
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) {

        String sessionId = session.getId();

        CacheDo cacheDo = CacheDo.builder().uid(uid).session(session).build();
        cache.add(sessionId, cacheDo);

        OnlineDo online = OnlineDo.builder().uid(uid).sessionId(sessionId).build();
        onlineMap.put(uid, online);

        log.info("{} 登录，session: {} 打开", uid, session.getId());

        // 登录成功，查看并获取未读消息列表中的消息

        // throw new RuntimeException("play some tricks");
    }

    @OnClose
    public void onClose(Session session) {

        String uid = getUid(session.getId());
        cache.delete(session.getId());

        onlineMap.remove(uid);

        log.info("{} 退出, session: {} 关闭", uid, session.getId());
    }

    @OnError
    public void onError(Session session, Throwable e) {

        String uid = getUid(session.getId());

        log.error("用户 {} session {} 出错: {}", uid, session.getId(), e.getCause().getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session) {

        String uid = getUid(session.getId());
        log.info("收到用户 {} session: {} 消息: {}", uid, session.getId(), message);

        messageDelivery(message);

        // sendToSelf(message, session);
    }

    @Deprecated
    private void sendToSelf(String message, Session session) {
        WSMessage srcMsg = JacksonUtils.readValue(message);
        WSMessage<Object> objMsg = WSMessage.builder().from("server").to(srcMsg.getFrom()).body("发送的消息内容为: " + srcMsg.getBody()).build();
        String jsonMsg = JacksonUtils.writeObjectAsString(objMsg);
        sendMessage(jsonMsg, session);
    }

    private void messageDelivery(String message) {

        WSMessage messageObj = JacksonUtils.readValue(message);

        log.info(messageObj.toString());

        if (Objects.isNull(messageObj.getGroup().getGid())) {
            sendToSingle(messageObj, message);
        } else {
            sendToGroup(messageObj, message);
        }

    }

    private void sendToSingle(WSMessage messageObj, String message) {
        String toId = messageObj.getTo();

        log.info("消息 to {} 处理转发逻辑...", toId);

        // 判断 to 是否在线
        if (onlineMap.containsKey(toId)) {
            // 在线，发送消息
            String toSessionId = onlineMap.get(toId).getSessionId();

            log.info("用户 {} 在线 session: {}，消息转发中...", toId, toSessionId);

            CacheDo toDo = cache.get(toSessionId);
            Session toSession = toDo.getSession();

            sendMessage(message, toSession);
        } else {
            // 不在线，进行消息存储
            log.info("用户 {} 不在线，消息存储中...", toId);

            // 首先查看消息 cache 中是否已经存在，已经存在的话添加到已存在的 list 中，
            // 不存在则新创建 list 进行存储
            if (messageCache.containsKey(toId)) {
                List<String> messages = messageCache.get(toId);
                messages.add(message);
            } else {
                List<String> messages = new ArrayList<>();
                messages.add(message);
                messageCache.put(toId, messages);
            }
        }
    }

    private void sendToGroup(WSMessage messageObj, String message) {
        String groupId = messageObj.getTo();

        log.info("群发消息 to {} 处理转发逻辑...", groupId);

        if (groups.containsKey(groupId)) {
            Set<String> member = groups.get(groupId);
            Set<String> memberSet = member.stream().filter(m -> !m.equals(messageObj.getFrom())).collect(Collectors.toSet());
            for (String uid : memberSet) {
                OnlineDo onlineDo = onlineMap.get(uid);

                // 过滤不在线的用户
                if (Objects.nonNull(onlineDo)) {
                    CacheDo cacheDo = cache.get(onlineDo.getSessionId());
                    Session toSession = cacheDo.getSession();
                    sendMessage(message, toSession);
                }
            }
        }
    }

    private void sendMessage(String message, Session to) {
        try {
            to.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.info("消息发送失败: {}", e.getCause().getMessage());
            e.printStackTrace();
        }
    }

    public List<OnlineDo> getContacts() {
        List<OnlineDo> list = onlineMap.values().stream().collect(Collectors.toList());
        return list;
    }

    public void createGroup(String groupId, String uid) {
        Set<String> member = new HashSet<>();
        member.add(uid);
        groups.put(groupId, member);

        log.info("create group: {} success", groupId);
    }

    public void updateGroup(String groupId, String uid) {
        Set<String> member = groups.get(groupId);
        if (!member.contains(uid)) {
            log.info("update group: {}, member: {}, success", groupId, uid);
            member.add(uid);
        }
    }

}
