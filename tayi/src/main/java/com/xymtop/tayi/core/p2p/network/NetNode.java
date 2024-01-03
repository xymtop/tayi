package com.xymtop.tayi.core.p2p.network;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 17:15
 */

@Data
public class NetNode {

    //编号
    private String id;

    //ip地址
    private String ip;

    //端口
    private  int port;

    //添加时间
    private  Long addTime;

    //最后一次ping时间
    private  Long lastPingTime;

    //状态
    private  boolean status;

    public NetNode(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.id = ip + ":" + port;
        this.addTime = System.currentTimeMillis();
        this.lastPingTime = System.currentTimeMillis();
        this.status = true;
    }
    

    public String getId(){
        return ip + ":" + port;
    }

}
