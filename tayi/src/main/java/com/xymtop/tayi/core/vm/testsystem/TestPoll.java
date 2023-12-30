package com.xymtop.tayi.core.vm.testsystem;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/28 16:25
 */

@Data
public class TestPoll {
     //测试方法
    private static Method method;

    //测试类
    static Object object;

    //测试参数

    static Object[] args;

    //测试结果
    Object result;

    //设置测试
    public static void   setTest(Method method,Object object,Object ...args){

    }

    //开始测试
    public void startTest() throws Exception {
        if (args == null)
            result = method.invoke(object);
        else
            result = method.invoke(object,args);
    }

}
