package com.xymtop.tayi.core.p2p.server.sender;

import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.p2p.network.NetNode;
import com.xymtop.tayi.core.p2p.server.entity.Msg;
import com.xymtop.tayi.core.p2p.server.utils.SocketUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 18:45
 */
public class MsgSender {

    public static  void sendPing(Socket socket) throws IOException {
        Msg pingMsg = Msg.createPingMsg();
        pingMsg =  getSender(socket,pingMsg);
        SocketUtils.sendMsg(socket, getSerializableString(pingMsg));
    }

    public  static  void sendPong(Socket socket) throws IOException {
        Msg pongMsg = Msg.createPongMsg();
        pongMsg =  getSender(socket,pongMsg);
        SocketUtils.sendMsg(socket, getSerializableString(pongMsg));
    }


    public  static  void  sendMsg(Socket socket, Msg msg) throws IOException {
        msg =  getSender(socket,msg);
        SocketUtils.sendMsg(socket, getSerializableString(msg));
    }

    //获取对象的可发送的序列字符串
    public  static  String getSerializableString(Object obj) throws IOException {
        return JSONUtil.toJsonStr(obj);
    }

    //获取当前socket的ip和端口
    static Msg getSender(Socket socket,Msg msg){
        String localAddress = socket.getLocalAddress().getHostAddress();
        int localPort = socket.getLocalPort();
        NetNode netNode = new NetNode(localAddress,localPort);

        msg.setNetNode(netNode);

        return msg;
    }
}
