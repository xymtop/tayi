package com.xymtop.tayi.core.oprate.poll;

import jakarta.websocket.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.xml.transform.sax.SAXResult;
import java.util.HashMap;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/2 19:12
 */

@Component
public class OpratePool {

    private HashMap<String, WebSocketSession> operatePoolCore = new HashMap<>();


    //写入池
    public void put(String key, WebSocketSession value){
        operatePoolCore.put(key,value);
    }

    //获取元素
    public WebSocketSession get(String key){

        WebSocketSession res = operatePoolCore.get(key);
        if (res != null){
            operatePoolCore.remove(key);
            return res;
        }else{
            return null;
        }
    }

}
