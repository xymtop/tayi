package com.xymtop.tayi.core.block;

import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.block.entity.BlockChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 4:41
 */
@Component
public class BlockChainUtils {

    @Autowired
    private BlockChain blockChain;

    @Autowired
    private BlockUtils blockUtils;

    @Autowired
    private GenesisBlock genesisBlock;

    @Autowired
    private BlockChainHeightUtils blockChainHeightUtils;

    //初始化区块链
    public void initBlockChain() throws Exception {
        blockChainHeightUtils.setBlockHeight(0);

        //加载创世区块
        Block gBlock = genesisBlock.loadGenesisBlock();

        //写入区块链
        addBlock(gBlock);
    }

    //添加区块到区块链
    public void addBlock(Block block) throws Exception {
        //写入区块链
        blockUtils.writeBlockToDB(block);

        //区块高度加一
        blockChainHeightUtils.addBlockHeight();
    }

    //获取某个区块
    public Block getBlock(long id) throws Exception {
        return blockUtils.getBlock(id);
    }

}
