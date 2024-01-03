package com.xymtop.tayi.core.starter;

import com.xymtop.tayi.core.system.Runner;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 18:27
 */

@Component
public class TaskSystemStart implements Runner {
    @Override
    public void run() throws Exception {
        System.out.println("TaskSystem Start");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
