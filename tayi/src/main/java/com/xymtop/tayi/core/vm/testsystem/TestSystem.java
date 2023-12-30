package com.xymtop.tayi.core.vm.testsystem;

import com.xymtop.tayi.TayiApplication;
import com.xymtop.tayi.core.system.SystemStatus;
import com.xymtop.tayi.core.system.TestObject;
import com.xymtop.tayi.core.vm.TaYiVM;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/28 15:32
 */



public class TestSystem {
    //直接运行和调试智能合约
    public static void RunContact(Class contracClass,String funName,Object ...args) throws Exception {
        //初始化运行的方法
        System.out.println("启动TaYi系统");
        //设置调试模式
        SystemStatus.debug = true;
        //启动springboot内核
        System.out.println("启动springboot内核");
        ConfigurableApplicationContext run = SpringApplication.run(TayiApplication.class);
        //写入测试队列
        System.out.println("写入测试队列");
        TestObject testObject = run.getBean(TestObject.class);
        testObject.setTestObject(contracClass);
        testObject.setFunName(funName);
    }

    public static void RunContact(Class contracClass,String funName,String[] sysArgs) throws Exception {
        //初始化运行的方法
        System.out.println("启动TaYi系统");
        //设置调试模式
        SystemStatus.debug = true;
        //启动springboot内核
        System.out.println("启动springboot内核");
        ConfigurableApplicationContext run = SpringApplication.run(TayiApplication.class,sysArgs);
        //写入测试队列
        System.out.println("写入测试队列");
        TestObject testObject = run.getBean(TestObject.class);
        testObject.setTestObject(contracClass);
        testObject.setFunName(funName);
    }

    public static void main(String[] args) throws Exception {
        TestSystem.RunContact(TestSystem.class,"test",args);
    }
}
