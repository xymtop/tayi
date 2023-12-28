package com.xymtop.tayi.core.graph;

import com.xymtop.tayi.core.block.entity.Block;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 0:53
 */
@Component
public class GraphBlockUtils {
    public void recoverGraphFromBlock(Block block) {

        System.out.println("正在恢复区块高度为"+block.getBlockNumber()+"的区块，哈希值为 "+block.getHash());
    }
}
