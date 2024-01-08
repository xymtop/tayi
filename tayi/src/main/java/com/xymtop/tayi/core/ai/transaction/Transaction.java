package com.xymtop.tayi.core.ai.transaction;

import com.xymtop.tayi.core.cmd.ExecResult;
import com.xymtop.tayi.core.oprate.OperateReceipt;
import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/7 2:12
 */


@Data
public class Transaction {

    //id
    String id;

    //签名
    String sign;


    String hash;

    //时间
    long timestamp;

    //交易的元数据

    TransactionMeta transactionMeta;

    //收据
    OperateReceipt operateReceipt;

    //执行结果
    ExecResult execResult;

}
