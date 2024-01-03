package com.xymtop.tayi.core.block.blockpackage;

import com.xymtop.tayi.core.block.BlockChainUtils;
import com.xymtop.tayi.core.block.BlockUtils;
import com.xymtop.tayi.core.block.builder.BlockBuilder;
import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.pool.PoolItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Override
    public void pack(List<PoolItem> items) throws Exception {

       //构建新区块
        Block newBlock = blockBuilder.build(items);

        //写入新区块
//        blockUtils.writeBlockToDB(newBlock);
        if (newBlock!=null){
            blockChainUtils.addBlock(newBlock);
        }



    }
}
