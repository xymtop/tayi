package com.xymtop.tayi.core.vm.code.that.impl;

import com.xymtop.tayi.core.block.BlockChainHeightUtils;
import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.vm.code.that.BlockUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 22:38
 */

@Component
@Lazy
public class BlockUtilsImpl implements BlockUtils {

    @Autowired
    private BlockChainHeightUtils blockChainHeightUtils;

    @Autowired
    com.xymtop.tayi.core.block.BlockUtils blockUtils;

    @Override
    public long getChainBlockHeight() {
        return blockChainHeightUtils.getBlockHeight();
    }

    @Override
    public Block getBlock(long blockNumber) throws Exception {
        return blockUtils.getBlock(blockNumber);
    }
}
