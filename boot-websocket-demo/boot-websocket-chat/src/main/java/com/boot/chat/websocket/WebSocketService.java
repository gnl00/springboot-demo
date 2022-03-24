package com.boot.chat.websocket;

import com.boot.chat.bean.CacheDo;
import com.boot.chat.cache.SessionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@ServerEndpoint(value = "/chat/{uid}")
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

    private static SessionCache cache;

    @Autowired
    public void setStore(SessionCache cache) {
        WebSocketService.cache = cache;
    }

    /**
     * 获取 ws 连接路径携带的参数
     * @ServerEndpoint(value = "/chat/{param}")
     * onOpen(Session session, @PathParam("param") String param)
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) {

        CacheDo cacheDo = CacheDo.builder().uid(uid).session(session).build();
        cache.add(session.getId(), cacheDo);

        log.info("{} 登录，session: {} 打开", uid, session.getId());

        // throw new RuntimeException("play some tricks");
    }

    @OnClose
    public void onClose(Session session) {

        String uid = getUid(session.getId());
        cache.delete(session.getId());

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
    }

    private String getUid(String sessionId) {
        CacheDo cacheDo = cache.get(sessionId);
        return cacheDo.getUid();
    }

}
