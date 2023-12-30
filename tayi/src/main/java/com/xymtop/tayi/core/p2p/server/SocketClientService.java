package com.xymtop.tayi.core.p2p.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/31 2:14
 */

public class SocketClientService {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public SocketClientService(String hostName, int port) throws IOException {
        socket = new Socket(hostName, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        SocketClientService client = new SocketClientService("localhost", 12345);

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("输入消息 (输入 'exit' 以退出):");
        while ((userInput = stdIn.readLine()) != null && !userInput.equalsIgnoreCase("exit")) {
            client.sendMessage(userInput);
            System.out.println("服务器响应: " + client.receiveMessage());
        }

        stdIn.close();
        client.close();
    }
}
