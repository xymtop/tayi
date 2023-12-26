package com.xymtop.tayi.core.task;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 17:48
 */
import com.xymtop.tayi.core.oprate.execute.ExecuterEntrance;
import com.xymtop.tayi.core.oprate.listener.PackageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private ExecuterEntrance executerEntrance;


    @Scheduled(fixedRate = 3000)
    public void scheduleTaskWithFixedRate() throws Exception {
        executerEntrance.startPackage(PackageType.TIME);
    }
}
