package com.xymtop.tayi.core.starter;

import com.xymtop.tayi.core.ai.AiMainNetwork;
import com.xymtop.tayi.core.system.Runner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/7 2:09
 */

@Component
public class AiSystemStart implements Runner {
    @Override
    public void run() throws Exception {

        System.out.println("AI System Start");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
