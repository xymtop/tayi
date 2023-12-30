package com.xymtop.tayi.core.block;

import com.xymtop.tayi.core.block.entity.BlockChain;
import com.xymtop.tayi.core.store.DBUtils;
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

    @Autowired
    DBUtils dbUtils;


    //获取当前区块高度
    public long getBlockHeight() {
        return blockChain.getBlockCount();
    }

    //调整区块高度
    public void setBlockHeight(long blockHeight) throws Exception {

        blockChain.setBlockCount(blockHeight);

        //写入数据库
        dbUtils.put("blockCount", String.valueOf(blockHeight));
    }

    //    区块高度加一
    public void addBlockHeight() throws Exception {
        long blockHeight = getBlockHeight();
        blockHeight++;
        setBlockHeight(blockHeight);
    }

}
