package com.xymtop.tayi.core.p2p.server.exec;

import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.block.BlockChainUtils;
import com.xymtop.tayi.core.block.blockpackage.Packager;
import com.xymtop.tayi.core.block.blockpool.BlockPool;
import com.xymtop.tayi.core.block.blockpool.ConsensusResult;
import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.p2p.server.entity.Msg;
import com.xymtop.tayi.core.utils.apputils.AppUtils;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 20:21
 */
public class ConsensusMsgExec {

    ApplicationContext applicationContext;

    Packager packager;

    BlockChainUtils blockChainUtils;
    public ConsensusMsgExec(){
         this.applicationContext = AppUtils.getApplicationContext();
         packager = applicationContext.getBean(Packager.class);
         blockChainUtils = applicationContext.getBean(BlockChainUtils.class);
    }
    public  void  exec(Msg msg) throws Exception {
        String msgMsg = String.valueOf(msg.getMsg());
        ConsensusResult consensusResult = JSONUtil.toBean(msgMsg, ConsensusResult.class);
        if (consensusResult.isResult()){

            //获取区块信息
            Block block = BlockPool.get(consensusResult.getBlockHash());
            if (block != null){

                blockChainUtils.addBlock(block);

                //共识完毕，返回操作结果并通知
                packager.returnResultBlock(block);

                System.out.println("收到一致性结果");
            }

        }else {
            System.out.println("收到不一致性结果");
        }
    }
}
