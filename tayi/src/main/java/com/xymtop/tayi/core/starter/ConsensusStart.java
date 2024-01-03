package com.xymtop.tayi.core.starter;

import com.xymtop.tayi.core.system.Runner;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 16:45
 */

@Component
public class ConsensusStart implements Runner {
    @Override
    public void run() throws Exception {
        System.out.println("ConsensusStart");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
