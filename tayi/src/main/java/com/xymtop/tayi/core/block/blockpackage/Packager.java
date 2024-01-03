package com.xymtop.tayi.core.block.blockpackage;

import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.browser.entity.Result;
import com.xymtop.tayi.core.block.BlockChainUtils;
import com.xymtop.tayi.core.block.BlockUtils;
import com.xymtop.tayi.core.block.blockpool.BlockPool;
import com.xymtop.tayi.core.block.builder.BlockBuilder;
import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.poll.OpratePool;
import com.xymtop.tayi.core.p2p.broadcast.BroadCastSender;
import com.xymtop.tayi.core.p2p.network.pool.NodePoll;
import com.xymtop.tayi.core.pool.PoolItem;
import jakarta.websocket.Session;
import org.springframework.web.socket.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 14:13
 */
@Component
public class Packager implements BlockPackage{

    @Autowired
    private BlockUtils blockUtils;

    @Autowired
    private BlockChainUtils blockChainUtils;

    @Autowired
    private BlockBuilder blockBuilder;

    @Autowired
    OpratePool opratePool;


    @Autowired
    BroadCastSender broadCastSender;
    @Override
    public void pack(List<PoolItem> items) throws Exception {

       //构建新区块
        Block newBlock = blockBuilder.build(items);

        //写入新区块
//        blockUtils.writeBlockToDB(newBlock);
        if (newBlock!=null){

            //共识算法,如果列表为空就不需要共识
            if (NodePoll.getNodeNum()==0){
                blockChainUtils.addBlock(newBlock);

                //共识完毕，返回操作结果并通知
                returnResultBlock(newBlock);

            }

            broadCastSender.sendBroadCast(newBlock);

            //写入区块池
            BlockPool.add(newBlock);

        }



    }

    public void returnResultBlock(Block newBlock) throws IOException {
        List<OperateEntity> operateList = newBlock.getOperateList();
        for (OperateEntity operate : operateList){
            WebSocketSession session = opratePool.get(operate.getOperateHash());
            if (session!=null){
                session.sendMessage(new TextMessage(JSONUtil.toJsonStr(Result.okData(operate))));
            }
        }
    }
}
