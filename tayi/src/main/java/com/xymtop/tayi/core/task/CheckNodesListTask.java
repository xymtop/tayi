package com.xymtop.tayi.core.task;

import com.xymtop.tayi.core.oprate.listener.PackageType;
import com.xymtop.tayi.core.p2p.network.pool.NodePoll;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 19:59
 */

@Component
public class CheckNodesListTask {
    @Scheduled(fixedRate = 10*1000)
    public void checkNodesList() throws Exception {
        System.out.println("开始维护节点列表");
        //更新节点状态
        NodePoll.startCheck();
    }

    @Scheduled(fixedRate = 20*1000)
    public void clearNodeList() throws Exception {
        System.out.println("开始清除节点");
        NodePoll.clearNodes();
    }
}
