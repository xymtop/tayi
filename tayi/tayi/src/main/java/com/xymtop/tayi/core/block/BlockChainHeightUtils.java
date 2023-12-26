package com.xymtop.tayi.core.block;

import com.xymtop.tayi.core.block.entity.BlockChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 4:47
 */
@Component
public class BlockChainHeightUtils {

    @Autowired
    private BlockChain blockChain;


    //获取当前区块高度
    public long getBlockHeight() {
        return blockChain.getBlockCount();
    }

    //调整区块高度
    public void setBlockHeight(long blockHeight) {
        blockChain.setBlockCount(blockHeight);
    }

    //    区块高度加一
    public void addBlockHeight() {
        long blockHeight = getBlockHeight();
        blockHeight++;
        setBlockHeight(blockHeight);
    }

}
