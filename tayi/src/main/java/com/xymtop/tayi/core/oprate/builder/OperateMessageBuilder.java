package com.xymtop.tayi.core.oprate.builder;

import com.xymtop.tayi.core.inter.Builder;
import com.xymtop.tayi.core.oprate.OperateMessage;
import com.xymtop.tayi.core.utils.encrypt.ECDSAUtil;
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
 * @date 2023/12/26 6:29
 */

@Component
public class OperateMessageBuilder implements Builder<String, OperateMessage> {

    @Autowired
    private XJsonUtils xJsonUtils;

    @Autowired
     private ECDSAUtil ecdsaUtil;

    @Override
    public OperateMessage build(String source) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        OperateMessage operateMessage = xJsonUtils.jsonToObj(source, OperateMessage.class);
//        if (!verifySignature(operateMessage)){
//            throw new RuntimeException("签名验证失败");
//        }
        return operateMessage;
    }

    //验证签名
    public boolean verifySignature(OperateMessage operateMessage) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //获取签名
        String signature = operateMessage.getSignature();
        //获取发送人
        String address = operateMessage.getAddress();
        //获取数据
        String data = operateMessage.getData();

        return ecdsaUtil.verify(ecdsaUtil.convertStringToPublicKey(address), xJsonUtils.objToJson(data).getBytes(), signature.getBytes());
    }
}
