package com.xymtop.tayi.core.vm.code.that;

import com.xymtop.tayi.core.block.entity.Block;

public interface BlockUtils {

    //获取区块高度
    long getChainBlockHeight();


    //获取某一个区块的具体内容
    Block getBlock(long blockNumber) throws Exception;


}
