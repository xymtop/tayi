package com.xymtop.tayi.core.oprate.builder;

import cn.hutool.core.lang.UUID;
import com.xymtop.tayi.core.inter.Builder;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.OperateMessage;
import com.xymtop.tayi.core.oprate.OperateType;
import com.xymtop.tayi.core.oprate.OprateMeta;

import com.xymtop.tayi.core.utils.jsonutils.XJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 5:16
 */
@Component
public class OperateEntityBuilder implements Builder<OperateMessage, OperateEntity> {

    @Autowired
    private   OprateMetaBuilder oprateMetaBuilder;

    @Autowired
    private XJsonUtils xJsonUtils;

    @Autowired
    UpdateBuilder updateBuilder;

    @Override
    public OperateEntity build(OperateMessage source) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //获取包含的数据
        String data = source.getData();
        //获取签名
        String signature = source.getSignature();
        //获取发送人
        String address = source.getAddress();

        //直接从data中自己拿
       OperateEntity operateEntity =   xJsonUtils.jsonToObj(data, OperateEntity.class);


       //设置发送者
        operateEntity.setSender(address);


        if (operateEntity.getOperateId()==null){
            //生成ID
            String id = UUID.fastUUID().toString(true);

            //设置ID
            operateEntity.setOperateId(id);
        }


//
//       //判断是什么类型
//        if (operateEntity.getOperateType()== OperateType.QUERY){
//
//        }
//
//        if (operateEntity.getOperateType()== OperateType.EXEC){
//            operateEntity =   updateBuilder.build(operateEntity);
//        }

        return operateEntity;
    }
}
