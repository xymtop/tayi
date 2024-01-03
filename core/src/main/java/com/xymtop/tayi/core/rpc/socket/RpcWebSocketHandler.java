package com.xymtop.tayi.core.rpc.socket;

import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.utils.jsonutils.XJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/31 2:07
 */

@Component
public class RpcWebSocketHandler implements WebSocketHandler {



    @Autowired
    private SocketExec socketExec;


    @Autowired
    XJsonUtils xJsonUtils;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 连接建立后的逻辑

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //处理消息
        Object payload = message.getPayload();

        //判断是否为JSON格式
        if (JSONUtil.isTypeJSON(String.valueOf(payload))){
            Object executed = socketExec.executeTransaction(payload);

            WebSocketMessage replaySocketMessage = new TextMessage(xJsonUtils.objToJson(executed));

            //回复消息
            session.sendMessage(replaySocketMessage);
        }



    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
