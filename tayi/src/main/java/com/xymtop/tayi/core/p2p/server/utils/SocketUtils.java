package com.xymtop.tayi.core.p2p.server.utils;

import java.io.*;
import java.net.Socket;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 18:37
 */
public class SocketUtils {

    //获取数据
    public  static String getMsgData(Socket socket) throws IOException {
        // 获取输入流
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        // 读取从服务器发送的所有数据
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        System.out.println("从服务器收到的全部消息: " + stringBuilder);

        // 关闭资源
        reader.close();
        input.close();
        return  stringBuilder.toString();
    }

    public  static  void sendMsg(Socket socket,String msg) throws IOException {
        OutputStream output = null;
        PrintWriter writer = null;

        try {
            // 获取输出流
             output = socket.getOutputStream();
             writer = new PrintWriter(output, true);
            writer.println(msg);

            System.out.println("发送信息: "+msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (writer != null) {
                    writer.close();
                }
                if (output != null) {
                    output.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
