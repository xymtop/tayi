package com.xymtop.tayi.core.oprate.execute;

import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.OperateMessage;
import com.xymtop.tayi.core.oprate.OperateReceipt;
import com.xymtop.tayi.core.oprate.OperateType;
import com.xymtop.tayi.core.oprate.builder.OperateEntityBuilder;
import com.xymtop.tayi.core.oprate.builder.OperateMessageBuilder;
import com.xymtop.tayi.core.oprate.poll.OpratePool;
import com.xymtop.tayi.core.pool.PoolItem;
import com.xymtop.tayi.core.pool.builder.PoolItemBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 6:31
 */

@Component
public class OperateEntrance {

    @Autowired
    private OperateMessageBuilder operateMessageBuilder;

    @Autowired
    private OperateEntityBuilder operateEntityBuilder;

    @Autowired
    private PoolItemBuilder poolItemBuilder;

    @Autowired
    private OpratePool opratePool;

    @Autowired
    private OperateExecuter operateExecuter;


    @Autowired
    private OperateEntityUtils operateEntityUtils;

    //处理操作消息
    public Object execute(String msg) throws Exception {
        //构建消息体
        OperateMessage operateMessage = operateMessageBuilder.build(msg);
         //构建操作数据实体
        OperateEntity operateEntity = operateEntityBuilder.build(operateMessage);

        //到这一步已经构建了查询的模式，根据不同的模式进行查询
        if (operateEntity.getOperateType()== OperateType.QUERY){

            //如果只是一个查询请求，直接在这里处理即可，不需要上链计算
            return getResult(operateEntity);
        }

        String poolItemUtilsHash = operateEntity.getOperateHash();




//        operateEntity.setOperateHash(poolItemUtilsHash);


        //构建操作item
        PoolItem poolItem = poolItemBuilder.build(operateEntity);


        //返回可以查询的ID
        return poolItemUtilsHash;
    }


    //执行查询的操作，比如查询结果
    public Object getResult(OperateEntity operate) throws Exception {

        OperateReceipt receipt = operateExecuter.execute(operate);

        String operateId = operate.getOperateHash();
        if (operateId == null){
            return "操作id缺失";
        }
        OperateEntity result = operateEntityUtils.getResult(operateId);

        return result;
    }
}
