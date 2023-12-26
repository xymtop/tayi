package com.xymtop.tayi.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 7:44
 */

@Component
public class RunTest {
    @Autowired
    private ApplicationContext applicationContext;

    public void run(){
       applicationContext.getBeansOfType(TestApp.class).forEach((k,v)->{
           try {
               v.test();
           } catch (NoSuchAlgorithmException e) {
               throw new RuntimeException(e);
           } catch (SignatureException e) {
               throw new RuntimeException(e);
           } catch (InvalidKeyException e) {
               throw new RuntimeException(e);
           }
       });
    }
}
