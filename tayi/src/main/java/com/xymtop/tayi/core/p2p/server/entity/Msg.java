package com.xymtop.tayi.core.p2p.server.entity;

import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.p2p.network.NetNode;
import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 18:51
 */

@Data
public class Msg {

    //发送者节点类型
    NetNode netNode;

    //类型
   MsgType msgType;

    //消息
    Object msg;

    //创建时间
    long createTime;

    public  Msg(MsgType msgType,Object msg){
        this.msg = msg;
        this.msgType = msgType;
        this.createTime = System.currentTimeMillis();
    }

    public static Msg createPingMsg(){
        return new Msg(MsgType.PING, "ping");
    }

    public  static  Msg createPongMsg(){
        return new Msg(MsgType.PONG, "pong");
    }

    //
    public static Msg createMsg(String msg){
        return new Msg(MsgType.BLOCK, msg);
    }

    public static  Msg createBlockMsg(Block block){
        return new Msg(MsgType.BLOCK, block);
    }
}
