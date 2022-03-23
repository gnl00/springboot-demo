package com.boot.chat.websocket;

import com.boot.chat.cache.SessionStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocketService
 *
 * @author lgn
 * @since 2022/3/22 10:08
 */

@Slf4j
@Service
@ServerEndpoint(value = "/chat/{param}")
public class WebSocketService {

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

    private static SessionStore store;

    @Autowired
    public void setStore(SessionStore store) {
        WebSocketService.store = store;
    }

    /**
     * 获取 ws 连接路径携带的参数
     * @ServerEndpoint(value = "/chat/{param}")
     * onOpen(Session session, @PathParam("param") String param)
     */
    @OnOpen
    public void onOpen(Session session) {
        store.add(session.getId(), session);
        log.info("open session: {}", session.getId());
        // throw new RuntimeException("play some tricks");
    }

    @OnClose
    public void onClose(Session session) {
        store.remove(session.getId());
        log.info("closing session: {}", session.getId());
    }

    @OnError
    public void onError(Session session, Throwable e) {
        log.error("WebSocketService 有链接 {} 出错啦~ {}", session.getId(), e.getCause().getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session) {}

}
