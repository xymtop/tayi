package com.xymtop.tayi;

import com.xymtop.tayi.system.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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

    @EventListener(ApplicationReadyEvent.class)
    public void  startBlockSystem() {
        applicationContext.getBeansOfType(Runner.class).values().forEach(Runner::run);
    }
}
