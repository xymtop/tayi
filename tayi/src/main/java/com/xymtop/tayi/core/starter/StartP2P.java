package com.xymtop.tayi.core.starter;


import com.xymtop.tayi.core.p2p.P2PStartNodeGeter;
import com.xymtop.tayi.core.p2p.server.SocketServerService;
import com.xymtop.tayi.core.system.Runner;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 2:43
 */
@Component
public class StartP2P implements Runner {

    @Autowired
    P2PStartNodeGeter p2PStartNodeGeter;
    @Override
    public void run() throws Exception {
        System.out.println("Start P2P");
        //获取最初的信息
        p2PStartNodeGeter.getNodes();
        SocketServerService serverService = new SocketServerService();
        serverService.startServer();
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
