package com.xymtop.tayi.p2p;

import com.xymtop.tayi.p2p.handler.NettyServerHandlerService;
import com.xymtop.tayi.system.Runner;
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
    private ApplicationContext applicationContext;


    private int port = 6666;



    @Override
    public void run() throws Exception {
        applicationContext.getBeansOfType(ChannelInboundHandlerAdapter.class).values().forEach(serverHandler -> {
            try {
                new NettyServer(port,serverHandler).start();
                port++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
