package com.xymtop.tayi.core.ai.transaction;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/8 21:32
 */

@Data
public class TransactionMeta {

    //操作的模型id
    String modelId;

    //类型
    TransactionType transactionType;


    //操作的模型参数
    Object[] modelParam;

}
