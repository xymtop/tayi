package com.xymtop.tayi.core.p2p.broadcast;

import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.p2p.network.NetNode;
import com.xymtop.tayi.core.p2p.network.pool.NodePoll;
import com.xymtop.tayi.core.p2p.server.entity.Msg;
import com.xymtop.tayi.core.p2p.server.sender.MsgSender;
import com.xymtop.tayi.core.p2p.server.sender.Sender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 20:08
 */

@Component
public class BroadCastSender {
    public void sendBroadCast(Block block) throws IOException {
        //获取全部节点列表
        Collection<NetNode> nodeList = NodePoll.getNodeList();
        for (NetNode node : nodeList){
            if (node.isStatus()){
                //发送区块链区块
                Socket socket = Sender.getSender(node.getIp(), node.getPort());
                MsgSender.sendMsg(socket, Msg.createBlockMsg(block));
            }
        }
    }


    public  void sendMsg(Msg msg)throws IOException {
        //获取全部节点列表
        Collection<NetNode> nodeList = NodePoll.getNodeList();
        for (NetNode node : nodeList){
            if (node.isStatus()){
                //发送区块链区块
                Socket socket = Sender.getSender(node.getIp(), node.getPort());
                MsgSender.sendMsg(socket, msg);
            }
        }
    }
}
