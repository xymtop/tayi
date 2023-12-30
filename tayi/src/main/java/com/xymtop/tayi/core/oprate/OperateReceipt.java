package com.xymtop.tayi.core.oprate;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 13:26
 */

//操作的收据
@Data
public class OperateReceipt {

    //操作的哈希
    private String operateHash;

//    操作在区块中序号
    private String operateIndex;

//    区块哈希
    private String blockHash;

//    区块序号
    private String blockIndex;

//    发起人
    private String sender;
//    接收者
    private String receiver;

//    状态
    private OperateStatus status;

}
