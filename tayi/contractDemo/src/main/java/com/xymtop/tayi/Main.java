package com.xymtop.tayi;

import com.xymtop.tayi.core.vm.testsystem.TestSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/28 15:43
 */
public class Main {

    public static void main(String[] args) throws Exception {
        TestSystem.RunContact(Application.class,"getHeight",args);
    }
}
