package com.xymtop.tayi.core.oprate.execute;

import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.OperateMessage;
import com.xymtop.tayi.core.oprate.builder.OperateEntityBuilder;
import com.xymtop.tayi.core.oprate.builder.OperateMessageBuilder;
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
    private OperateEntityUtils operateEntityUtils;

    //处理操作消息
    public String execute(String msg) throws Exception {
        //构建消息体
        OperateMessage operateMessage = operateMessageBuilder.build(msg);
         //构建操作数据实体
        OperateEntity operateEntity = operateEntityBuilder.build(operateMessage);

        String poolItemUtilsHash = operateEntityUtils.getHash(operateEntity);

        operateEntity.setOperateHash(poolItemUtilsHash);


        //构建操作item
        PoolItem poolItem = poolItemBuilder.build(operateEntity);


        //返回可以查询的ID
        return poolItemUtilsHash;
    }
}
