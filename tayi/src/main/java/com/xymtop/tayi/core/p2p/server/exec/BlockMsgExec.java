package com.xymtop.tayi.core.p2p.server.exec;

import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.block.blockpool.ConsensusResult;
import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.p2p.broadcast.BroadCastSender;
import com.xymtop.tayi.core.p2p.network.NetNode;
import com.xymtop.tayi.core.p2p.server.entity.Msg;
import com.xymtop.tayi.core.p2p.server.entity.MsgType;
import com.xymtop.tayi.core.p2p.server.sender.MsgSender;
import com.xymtop.tayi.core.p2p.server.sender.Sender;
import com.xymtop.tayi.core.utils.apputils.AppUtils;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.Socket;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 19:03
 */
public class BlockMsgExec {

    public void exec(Msg msg) throws IOException {
        ApplicationContext applicationContext = AppUtils.getApplicationContext();
        //共识区块消息
        NetNode netNode = msg.getNetNode();
        String blockStr = String.valueOf( msg.getMsg());
        Block block = JSONUtil.toBean(blockStr, Block.class);
        Socket socket = Sender.getSender(netNode.getIp(), netNode.getPort());
        Msg conMsg = new Msg(MsgType.CONSENSUS, new ConsensusResult(true, block.getHash()));

        BroadCastSender broadCastSender = applicationContext.getBean(BroadCastSender.class);

        //发送自己的共识信息
        broadCastSender.sendMsg(conMsg);



    }
}
