package com.xymtop.tayi.core.p2p.server.exec;

import com.xymtop.tayi.core.p2p.network.NetNode;
import com.xymtop.tayi.core.p2p.network.pool.NodePoll;
import com.xymtop.tayi.core.p2p.server.entity.Msg;
import com.xymtop.tayi.core.p2p.server.entity.MsgType;
import com.xymtop.tayi.core.p2p.server.sender.MsgSender;
import com.xymtop.tayi.core.p2p.server.sender.Sender;

import java.io.IOException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 19:02
 */
public class PingPongMsgExec {


    public void exec(Msg msg) throws IOException {

        if (msg.getMsgType() == MsgType.PING){
            execPing(msg);
        }else {
            execPong(msg);
        }
    }

    public  void execPing(Msg msg) throws IOException {

        //回复一个pong
        NetNode netNode = msg.getNetNode();
        MsgSender.sendPong(Sender.getSender(netNode.getIp(),netNode.getPort()));

    }

    public  void execPong(Msg msg){
        //查看是否在当前的列表里面
        NetNode node = NodePoll.getNode(msg.getNetNode());
        NodePoll.setNode(msg.getNetNode());
    }
}
