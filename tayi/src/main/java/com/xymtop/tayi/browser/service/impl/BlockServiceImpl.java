package com.xymtop.tayi.browser.service.impl;

import com.xymtop.tayi.browser.service.BlockService;
import com.xymtop.tayi.core.block.BlockChainHeightUtils;
import com.xymtop.tayi.core.block.BlockChainUtils;
import com.xymtop.tayi.core.block.BlockUtils;
import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.block.GenesisBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 4:27
 */

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private GenesisBlock genesisBlock;

    @Autowired
    private BlockChainUtils blockChainUtils;


    @Autowired
    private BlockChainHeightUtils blockChainHeightUtils;


    @Override
    public Block getGenesisBlock() {
        return genesisBlock.loadGenesisBlock();
    }

    @Override
    public Block getBlock(long id) throws Exception {

        return blockChainUtils.getBlock(id);
    }

    @Override
    public long getBlockHeight() {
        return blockChainHeightUtils.getBlockHeight();
    }
}
