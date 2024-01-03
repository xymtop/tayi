package com.xymtop.tayi.core.p2p.server.exec;

import com.xymtop.tayi.core.p2p.network.NetNode;
import com.xymtop.tayi.core.p2p.network.pool.NodePoll;
import com.xymtop.tayi.core.p2p.server.entity.Msg;

import java.util.Collection;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 21:11
 */
public class ReturnNodesMsgExec {

    public void  exec(Msg msg){
        //获取到数据
        Collection<NetNode> nodes =   (Collection<NetNode>)  msg.getMsg();

        for (NetNode node : nodes){
            NodePoll.setNode(node);
        }
    }
}
