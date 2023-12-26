package com.xymtop.tayi.core.block;

import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.block.entity.BlockChain;
import com.xymtop.tayi.core.graph.GraphBlockUtils;
import com.xymtop.tayi.core.store.DBUtils;
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
    DBUtils dbUtils;

    @Autowired
    private BlockChainHeightUtils blockChainHeightUtils;


    @Autowired
    GraphBlockUtils graphBlockUtils;

    //初始化区块链
    public void initBlockChain() throws Exception {

         if (isInitFromDB()){
             initBlockChainFromDB();
             return;
         }

        blockChainHeightUtils.setBlockHeight(0);

        //加载创世区块
        Block gBlock = genesisBlock.loadGenesisBlock();

        //写入区块链
        addBlock(gBlock);
    }


    //判断是否应该从数据库中加载
    public boolean isInitFromDB() throws Exception {
        if (blockChain.getBlockCount() == 0){
            String blockCount = dbUtils.get("blockCount");

            if (blockCount == null){
                return false;
            }

            long  height =  Long.valueOf(blockCount);

             if (height == 0){
                 return false;
             }else {
                 return true;
             }
        }
        //如果区块不为空，说明区块链中有区块，直接返回true
        return false;
    }

    //从数据库中初始化区块链
    public void initBlockChainFromDB() throws Exception {
        //获取区块高度
        long blockHeight = Long.valueOf( dbUtils.get("blockCount"));

        blockChainHeightUtils.setBlockHeight(blockHeight);

        //循环获取区块
        for (long i = 0; i < blockHeight; i++) {
            Block block = blockUtils.getBlock(i);

            //从区块中验证并恢复图数据
            graphBlockUtils.recoverGraphFromBlock(block);
        }
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
