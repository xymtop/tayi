package com.xymtop.tayi.core.cmd.apis;

import com.xymtop.tayi.core.block.BlockUtils;
import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.cmd.apis.ann.CmdApi;
import com.xymtop.tayi.core.cmd.apis.ann.CmdApiFun;
import com.xymtop.tayi.core.block.BlockChainHeightUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 21:05
 */

@Component
@CmdApi
public class BlockChainApi {

    @Autowired
    private BlockChainHeightUtils blockChainHeightUtils;

    @Autowired
    BlockUtils blockUtils;

    //获取区块高度
    @CmdApiFun(cmd = "getBlockHeight")
    public long getBlockHeight(){
        return blockChainHeightUtils.getBlockHeight();
    }

    //获取系统时间戳
    @CmdApiFun(cmd = "getTimestamp")
    public long getTimestamp(){
        return System.currentTimeMillis();
    }

    //获取当前区块id
    @CmdApiFun(cmd = "getBlockId")
    public long getBlockId(){
        return blockChainHeightUtils.getBlockHeight()+1;
    }


    //获取区块信息
    @CmdApiFun(cmd = "getLastBlock")
    public Block getLastBlock() throws Exception {
        return blockUtils.getBlock(getBlockHeight());
    }


}
