package com.xymtop.tayi.core.system;

import com.xymtop.tayi.core.vm.TaYiVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/28 16:54
 */

@Component
public class TestStarter {

    @Autowired
    private TaYiVM taYiVM;

    @Autowired
    private TestObject testObject;

    public void startTest() throws Exception {

        //获取TaYi虚拟机
        System.out.println("获取TaYi虚拟机");
        if (taYiVM == null) {
            System.out.println("taYiVM获取失败");
            return;
        }
        System.out.println("TaYi系统启动成功");

        //获取测试队列
        System.out.println("获取测试队列");

        if (testObject == null) {
            System.out.println("测试队列获取失败");
            return;
        }

        if (testObject.getArgs() == null) {
            Object object =   taYiVM.debug(testObject.getTestObject(), testObject.getFunName());
        }else {
            //运行智能合约
            Object object =   taYiVM.debug(testObject.getTestObject(), testObject.getFunName(),testObject.getArgs());
        }

        System.out.println("调试结束");
    }
}
