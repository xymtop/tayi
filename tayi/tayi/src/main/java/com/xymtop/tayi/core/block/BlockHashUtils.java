package com.xymtop.tayi.core.block;

import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.utils.encrypt.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 3:17
 */
@Component
public class BlockHashUtils {

    @Autowired
    private HashUtils hashUtils;

    @Autowired
    private BlockUtils blockUtils;

    //计算某个区块的哈希
    public String getBlockHash(Block block) {
        //获取构建区块哈希的属性
        String blockHashAttr = blockUtils.getBlockHashAttr(block);
        //计算哈希
        return hashUtils.hashHex(blockHashAttr);
    }

}
