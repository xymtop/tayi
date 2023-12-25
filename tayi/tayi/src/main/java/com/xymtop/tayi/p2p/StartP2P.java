package com.xymtop.tayi.p2p;

import com.xymtop.tayi.system.Runner;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 2:43
 */
@Component
public class StartP2P implements Runner {

    @Override
    public void run() {
        System.out.println("P2P网络启动");
    }
}
