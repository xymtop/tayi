package com.xymtop.tayi.core.p2p.server.exec;

import com.xymtop.tayi.core.p2p.server.entity.Msg;
import com.xymtop.tayi.core.p2p.server.entity.MsgType;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 18:41
 */


public class MsgExec {

    PingPongMsgExec pingPongMsgExec = new PingPongMsgExec();

    BlockMsgExec blockMsgExec = new BlockMsgExec();


    GetNodesListMsgExec getNodesListMsgExec = new GetNodesListMsgExec();

    ConsensusMsgExec consensusMsgExec = new ConsensusMsgExec();


    ReturnNodesMsgExec returnNodesMsgExec = new ReturnNodesMsgExec();

    public void exec(Msg msg) throws Exception {
        if (msg.getMsgType()== MsgType.PING||msg.getMsgType()==MsgType.PONG){
            pingPongMsgExec.exec(msg);
        }else  if (msg.getMsgType() == MsgType.BLOCK){
            blockMsgExec.exec(msg);
        }else if (msg.getMsgType() == MsgType.GETNODES){
            getNodesListMsgExec.exec(msg);
        }else if (msg.getMsgType() == MsgType.CONSENSUS){
            consensusMsgExec.exec(msg);
        }else if (msg.getMsgType()==MsgType.RETURNNODES){
            returnNodesMsgExec.exec(msg);
        }
    }
}
