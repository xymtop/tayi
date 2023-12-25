package com.xymtop.tayi.core.block;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 2:18
 */
@Data
public class BlockHead {

    //区块版本
    private String version;

//    前一个区块的哈希值
    private String previousHash;

//    merkle树根节点的哈希值
    private String merkleRootHash;
//    时间戳
    private String timeStamp;
//    难度目标
    private String target;
//    随机数
    private String nonce;
}
