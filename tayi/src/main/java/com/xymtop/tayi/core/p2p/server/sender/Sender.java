package com.xymtop.tayi.core.p2p.server.sender;

import java.io.IOException;
import java.net.Socket;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 19:18
 */
public class Sender {

    public  synchronized static Socket getSender(String ip,int port) throws IOException {

        return new Socket(ip,port);
    }
}
