package com.boot.websocket;

import com.boot.entities.Violation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * ViolationHandler
 *
 * @author lgn
 * @since 2021/12/23 10:50
 */

@ServerEndpoint("/imap-web/websocket/violation")
@Component
public class ViolationHandler {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("有新的连接加入 violation: " + session.getId());

        try {
            Thread.sleep(3000);

            sendMsg(session);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("有链接关闭: " + session.getId());
    }

    // 发送消息给客户端
    public void sendMsg(Session toSession) {
        try {

            while (true) {
                Violation vio = new Violation("camera1",
                        "camera1",
                        1,
                        "4621caccdde14c8f9964748f2b9f76c1",
                        "road_controll",
                        "eastWest",
                        "http://10.19.166.190:6120/pic?did=1&bid=911&pid=1073743904&size=275520&ptime=1640053888",
                        "http://10.19.166.190:6120/pic?did=1&bid=911&pid=1073743904&size=275520&ptime=1640053888",
                        "由东向西-车道1",
                        "1",
                        "677081E76D133042BF24B0D670E9D56B",
                        "2021-12-21 13:31:36",
                        "",
                        1,
                        "",
                        "blue",
                        "http://10.19.166.190:6120/pic?did=1&bid=911&pid=1073743900&size=3480&ptime=1640053888",
                        "浙AG8D36",
                        "815.0,815.0,164.0,42.0",
                        "unknown",
                        "5eacf67d-810b-4c40-8047-6a0abb03674e",
                        "deepBlue",
                        "0",
                        "65",
                        "unknown",
                        "",
                        "E38AF764-4D91-B847-940B-7EE3033D6001",
                        "4621caccdde14c8f9964748f2b9f76c1",
                        "Wrong-Way Driving"
                        );

                Violation vio1 = new Violation("IPCamera1",
                        "IPCamera1",
                        1,
                        "fa135067ef8c451cb34c3d5094c51a83",
                        "road_controll",
                        "eastWest",
                        "http://10.19.167.249:6120/pic?640DB08B0EC02B05A6F3*hcs53b932fc359d4bada2021/001/17676;16401737816555943714909?pic*747422652*574680*714*640DB08B0EC02B05A6F3-2*1640177369",
                        "",
                        "由东向西-车道1",
                        "1",
                        "677081E76D133042BF24B0D670E9D56B",
                        "2021-12-21 13:31:36",
                        "",
                        1,
                        "",
                        "blue",
                        "http://10.19.166.190:6120/pic?did=1&bid=911&pid=1073743900&size=3480&ptime=1640053888",
                        "浙AG8D36",
                        "815.0,815.0,164.0,42.0",
                        "unknown",
                        "5eacf67d-810b-4c40-8047-6a0abb03674e",
                        "deepBlue",
                        "0",
                        "65",
                        "unknown",
                        "",
                        "E38AF764-4D91-B847-940B-7EE3033D6001",
                        "4621caccdde14c8f9964748f2b9f76c1",
                        "Wrong-Way Driving"
                );

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonObj = objectMapper.writeValueAsString(vio);

                toSession.getBasicRemote().sendText(jsonObj);

                // 25s 发送一次数据
                Thread.sleep(1000 * 16);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
