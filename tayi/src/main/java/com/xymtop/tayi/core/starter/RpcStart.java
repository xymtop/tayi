package com.xymtop.tayi.core.starter;

import com.xymtop.tayi.core.system.Runner;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 2:55
 */
@Component
public class RpcStart implements Runner {


    @Override
    public void run() {
        System.out.println("rpc start");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
