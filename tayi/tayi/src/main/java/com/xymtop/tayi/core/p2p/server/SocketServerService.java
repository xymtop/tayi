package com.xymtop.tayi.core.p2p.server;

import jakarta.annotation.PostConstruct;
import org.jvnet.hk2.annotations.Service;

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

    private static final int PORT = 12345; // 定义端口号

    @PostConstruct
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                // 处理连接
                handleClientSocket(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket(Socket socket) {
        // 实现客户端通信逻辑
    }
}
