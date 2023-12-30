package com.xymtop.tayi.core.vm.virtual;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 14:46
 */

@Component
public class TaYiContractLoader {

    @Value("${tayi.vm.contract.path}")
    private String contractPath;

    //通过jar路径加载合约
    public void load(String jarPath) {
        System.out.println("加载合约中...");
    }


    //部署合约
    public void deploy(String jarPath) {
        System.out.println("部署合约中...");
    }

    //调用合约
    public void call(Object ...args) {
        System.out.println("调用合约中...");
    }

}
