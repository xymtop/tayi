package com.xymtop.tayi.core.p2p;

import com.xymtop.tayi.core.p2p.network.NetNode;
import com.xymtop.tayi.core.p2p.network.pool.NodePoll;
import com.xymtop.tayi.core.p2p.server.entity.Msg;
import com.xymtop.tayi.core.p2p.server.entity.MsgType;
import com.xymtop.tayi.core.p2p.server.entity.StartNode;
import com.xymtop.tayi.core.p2p.server.sender.MsgSender;
import com.xymtop.tayi.core.p2p.server.sender.Sender;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 21:15
 */


@Component
public class P2PStartNodeGeter {

    @Autowired
    private StartNode startNode;



    //从最开始的节点获取数据
    public void  getNodes() throws IOException {
        if (startNode != null){

            NetNode netNode = new NetNode(startNode.getIp(),startNode.getPort());
            NodePoll.setNode(netNode);

            Socket socket = Sender.getSender(startNode.getIp(), startNode.getPort());
            MsgSender.sendMsg(socket,new Msg(MsgType.GETNODES,""));
        }
    }

    public void askNodes(){
        
    }
}
