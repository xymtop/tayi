package com.xymtop.tayi.browser.service;

import com.xymtop.tayi.core.block.entity.Block;

public interface BlockService {
    //获取创世区块
     Block getGenesisBlock();

    Block getBlock(long id) throws Exception;

    long getBlockHeight();
}
