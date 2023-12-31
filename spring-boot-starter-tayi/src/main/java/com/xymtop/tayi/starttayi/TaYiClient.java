package com.xymtop.tayi.starttayi;

import com.xymtop.tayi.starttayi.config.WebSocketClient;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/31 23:12
 */

@Data
public class TaYiClient {

    String ip;

    String user;

    public TaYiClient(String ip,String user){
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            String uri = ip; // 用实际的WebSocket服务器地址替换
            container.connectToServer(WebSocketClient.class, URI.create(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
