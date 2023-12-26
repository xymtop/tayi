package com.xymtop.tayi.cmd;

import com.xymtop.tayi.system.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 21:09
 */

@Component
public class CmdSystemStart implements Runner {

    @Autowired
    private CmdSystem cmdSystem;
    @Override
    public void run() throws Exception {
        cmdSystem.init();
    }
}
