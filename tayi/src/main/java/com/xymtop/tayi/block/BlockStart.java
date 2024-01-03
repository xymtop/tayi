package com.xymtop.tayi.block;

import com.xymtop.tayi.core.block.BlockChainUtils;
import com.xymtop.tayi.core.system.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 4:59
 */

@Component
public class BlockStart implements Runner {

    @Autowired
    private BlockChainUtils blockChainUtils;

    //启动流程
    private void start() throws Exception {
        //初始化区块链
        blockChainUtils.initBlockChain();


        //同步数据

        //启动区块链
    }

    @Override
    public void run() throws Exception {
        start();
        System.out.println("区块链启动");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
