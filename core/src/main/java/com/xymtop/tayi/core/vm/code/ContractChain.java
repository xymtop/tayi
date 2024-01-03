package com.xymtop.tayi.core.vm.code;

import com.xymtop.tayi.core.block.BlockChainHeightUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 15:34
 */

@Component
public class ContractChain {

    @Autowired
    private BlockChainHeightUtils blockChainHeightUtils;
    //获取区块高度
    public  long getBlockHeight(){
        return blockChainHeightUtils.getBlockHeight();
    }

}
