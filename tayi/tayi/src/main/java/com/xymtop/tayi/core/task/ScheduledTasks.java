package com.xymtop.tayi.core.task;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 17:48
 */
import com.xymtop.tayi.core.oprate.execute.ExecuterEntrance;
import com.xymtop.tayi.core.oprate.listener.PackageType;
import com.xymtop.tayi.core.pool.Pool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private ExecuterEntrance executerEntrance;

    @Autowired
    Pool pool;


    @Scheduled(fixedRate = 1000)
    public void scheduleTaskWithFixedRate() throws Exception {

        if (pool.getSize()<10){
            executerEntrance.startPackage(PackageType.TIME);
        }else{
            executerEntrance.startPackage(PackageType.NUM);
        }
    }
}
