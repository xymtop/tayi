package com.xymtop.tayi.store;

import com.xymtop.tayi.system.Runner;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 2:46
 */
@Component
public class StoreStart implements Runner {

    @Override
    public void run() {
        System.out.println("数据储存启动");
    }
}
