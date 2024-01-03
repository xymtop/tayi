package com.xymtop.tayi.core.p2p.server;

import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.p2p.server.config.NetworkConfig;
import com.xymtop.tayi.core.p2p.server.entity.Msg;
import com.xymtop.tayi.core.p2p.server.exec.MsgExec;
import com.xymtop.tayi.core.p2p.server.utils.SocketUtils;
import com.xymtop.tayi.core.utils.apputils.AppUtils;
import jakarta.annotation.PostConstruct;
import org.jvnet.hk2.annotations.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/31 2:11
 */
@Service
public class SocketServerService {

    private static final int PORT = 9999; // 定义端口号



    @PostConstruct
    public void startServer() {
        ApplicationContext applicationContext = AppUtils.getApplicationContext();
        NetworkConfig networkConfig = applicationContext.getBean(NetworkConfig.class);


        try {
            ServerSocket serverSocket = new ServerSocket(networkConfig.getPort());
//            ServerSocket serverSocketTest = new ServerSocket(networkConfig.getPort()-1);
            while (true) {
                Socket socket = serverSocket.accept();
                // 处理连接
                handleClientSocket(socket);

//                Socket testSocket = serverSocketTest.accept();
//                handleClientSocket(testSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleClientSocket(Socket socket) throws Exception {
        //获取数据
        String msgData = SocketUtils.getMsgData(socket);

        if (msgData != null){
            //判断是否为json字符串
            if (JSONUtil.isTypeJSON(msgData)){
                Msg msg = JSONUtil.toBean(msgData, Msg.class);

                if (msg!=null){
                    MsgExec msgExec = new MsgExec();
                    //处理msg
                    msgExec.exec(msg);
                }
            }
        }
    }


}
