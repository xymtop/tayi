package com.xymtop.tayi.core.block.blockpool;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 20:29
 */

@Data
public class ConsensusResult {
    //共识结果
    boolean result;

    //共识的区块哈希
    String blockHash;

    public ConsensusResult(boolean result, String blockHash){
        this.result = result;
        this.blockHash = blockHash;
    }

}
