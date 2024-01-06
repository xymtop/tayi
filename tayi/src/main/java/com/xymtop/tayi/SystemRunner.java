package com.xymtop.tayi;

import com.xymtop.tayi.core.system.Runner;
import com.xymtop.tayi.core.system.SystemStatus;
import com.xymtop.tayi.core.system.TestStarter;
import com.xymtop.tayi.core.utils.apputils.AppUtils;
import com.xymtop.tayi.test.TestApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 2:49
 */

//所有系统的启动入口，springboot启动完成后再依次启动分系统
@Component
public class SystemRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    TestStarter testStarter;

    @EventListener(ApplicationReadyEvent.class)
    public void  startBlockSystem() throws Exception {

        //设置app
        AppUtils.setApplicationContext(applicationContext);
//        if (SystemStatus.debug) {
//             testStarter.startTest();
//        }
        Collection<Runner> values = applicationContext.getBeansOfType(Runner.class).values();
        values.stream().sorted((o1, o2) -> o1.getOrder() - o2.getOrder());
        for (Runner runner : values){
            try {
                runner.run();
            }catch (Exception e){
                throw e;
            }

        }

        System.out.println("系统启动完毕！");
    }
}
