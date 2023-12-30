package com.xymtop.tayi.core.oprate.builder;

import com.xymtop.tayi.core.inter.Builder;
import com.xymtop.tayi.core.oprate.OperateEntity;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 8:32
 */

@Component
public class UpdateBuilder implements Builder<OperateEntity,OperateEntity> {

    @Override
    public OperateEntity build(OperateEntity source) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        return null;
    }
}
