package com.xymtop.tayi.test;

import com.xymtop.tayi.core.oprate.execute.OperateEntrance;
import com.xymtop.tayi.core.vm.TaYiVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    @Autowired
    TaYiVM taYiVM;

    @Override
    public void test() throws Exception {
//        taYiVM.deploy("QmZk5PYiLTXcfTzDKKLzLQg1ipKsNdehcMzC5XgSQzv9Jc");
        String id = taYiVM.deploy("QmZQFoKwSPf1sbmPFiRTE8MJKVRDxdY6P6uvnnyBRtKdJe");

        System.out.println("合约id:"+id);
        Object res = taYiVM.call(id, "hello", new String[]{"TaYi"});
        System.out.println("调用结果:"+res);
    }
}
