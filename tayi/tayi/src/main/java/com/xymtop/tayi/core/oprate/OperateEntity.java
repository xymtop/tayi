package com.xymtop.tayi.core.oprate;

import com.xymtop.tayi.cmd.ExecResult;
import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: 操作体
 * @date 2023/12/26 1:16
 */

@Data
public class OperateEntity {

    //操作编号
    private String operateId;

    //操作的哈希值
    private String operateHash;

//    操作类型
    private OperateType operateType;

    //操作指令
    private String operateCmd;

    //发起人
    private String sender;


//    接收者
    private String recipient;


//时间戳
    private long timestamp;

    //涉及的NFT
    private String nftId;


//    用户操作次数
    private long nonce;


    private  OprateMeta oprateMeta;

    //操作的收据
    private OperateReceipt operateReceipt;

    //操作的结果
    private ExecResult execResult;

}
