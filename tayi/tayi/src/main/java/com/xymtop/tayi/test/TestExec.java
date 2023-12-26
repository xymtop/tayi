package com.xymtop.tayi.test;

import com.xymtop.tayi.core.oprate.execute.OperateEntrance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 7:42
 */

@Component
public class TestExec implements TestApp{

    @Autowired
    private OperateEntrance operateEntrance;

    @Override
    public void test() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

    }
}
