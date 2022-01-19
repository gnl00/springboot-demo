package com.boot.websocket;

import com.boot.entities.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * VehicleHandler
 *
 * @author lgn
 * @since 2021/12/23 10:49
 */

@ServerEndpoint("/imap-web/websocket/vehicle")
@Component
public class VehicleHandler {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("有新的连接加入 vehicle: " + session.getId());

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

                Vehicle veh1 = new Vehicle("5f4786815c5745f9a7ede0493fa91acd",
                        "47",
                        "47",
                        "http://10.19.167.249:6120/pic?640DB08B0EC02B05A6F3*hcs53b932fc359d4bada2021/001/17676;16401737816555943714909?pic*747422652*574680*714*640DB08B0EC02B05A6F3-2*1640177369",
                        "32C11796780D4547BD937CF5ABCD9089",
                        "2021-12-22 20:48:53",
                        "浙A6574C");

                Vehicle veh2 = new Vehicle("fac6e1bccfa7445796b6e93d5d8c64f9",
                        "IPCamera2",
                        "IPCamera2",
                        "http://10.19.167.249:6120/pic?640DB08B0EC02B05A6F3*hcs53b932fc359d4bada2021/001/17676;16401737816555943714909?pic*747422652*574680*714*640DB08B0EC02B05A6F3-2*1640177369",
                        "32C11796780D4547BD937CF5ABCD9089",
                        "2021-12-22 20:48:53",
                        "浙A6574C");

                Vehicle veh3 = new Vehicle("4621caccdde14c8f9964748f2b9f76c1",
                        "camera1",
                        "camera1",
                        "http://10.19.167.249:6120/pic?640DB08B0EC02B05A6F3*hcs53b932fc359d4bada2021/001/17676;16401737816555943714909?pic*747422652*574680*714*640DB08B0EC02B05A6F3-2*1640177369",
                        "32C11796780D4547BD937CF5ABCD9089",
                        "2021-12-22 20:48:53",
                        "浙A6574C");

                Vehicle veh4 = new Vehicle("fa135067ef8c451cb34c3d5094c51a83",
                        "IPCamera1",
                        "IPCamera1",
                        "http://10.19.167.249:6120/pic?640DB08B0EC02B05A6F3*hcs53b932fc359d4bada2021/001/17676;16401737816555943714909?pic*747422652*574680*714*640DB08B0EC02B05A6F3-2*1640177369",
                        "32C11796780D4547BD937CF5ABCD9089",
                        "2021-12-22 20:48:53",
                        "浙A6574C");

                List<Vehicle> vehicleList = new ArrayList<>();

                vehicleList.add(veh1);
                vehicleList.add(veh2);
                vehicleList.add(veh3);
                vehicleList.add(veh4);

                ObjectMapper objectMapper = new ObjectMapper();

                for (int i = 0; i < vehicleList.size(); i++) {
                    String jsonObj = objectMapper.writeValueAsString(vehicleList.get(i));
                    toSession.getBasicRemote().sendText(jsonObj);

                    Thread.sleep(1000 * 1);
                }

                // 5s 发送一次数据
                Thread.sleep(1000 * 5);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
