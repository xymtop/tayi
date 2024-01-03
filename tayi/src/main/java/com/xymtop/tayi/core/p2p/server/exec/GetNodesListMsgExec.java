package com.xymtop.tayi.core.p2p.server.exec;

import com.xymtop.tayi.core.p2p.network.NetNode;
import com.xymtop.tayi.core.p2p.network.pool.NodePoll;
import com.xymtop.tayi.core.p2p.server.entity.Msg;
import com.xymtop.tayi.core.p2p.server.entity.MsgType;
import com.xymtop.tayi.core.p2p.server.sender.MsgSender;
import com.xymtop.tayi.core.p2p.server.sender.Sender;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 19:06
 */
public class GetNodesListMsgExec {

    public  void exec(Msg msg) throws IOException {
        //获取节点列表
        Collection<NetNode> nodeList = NodePoll.getNodeList();

        //发送节点信息
        NetNode netNode = msg.getNetNode();

        Socket socket = Sender.getSender(netNode.getIp(), netNode.getPort());

        MsgSender.sendMsg(socket,new Msg(MsgType.RETURNNODES,nodeList));
    }
}
