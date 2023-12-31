package com.xymtop.tayi.starttayi.config;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/31 23:35
 */


import javax.websocket.*;

@ClientEndpoint
public class WebSocketClient {

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session " + session.getId() + " closed because of " + closeReason);
    }

}
