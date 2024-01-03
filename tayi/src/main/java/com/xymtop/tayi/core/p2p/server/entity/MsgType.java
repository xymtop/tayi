package com.xymtop.tayi.core.p2p.server.entity;

public enum MsgType {

    //探测消息
    PING(1),
    PONG(2),

    //区块消息
    BLOCK(3),

    CONSENSUS(6),

    //返回节点列表
    RETURNNODES(5),

    //获取节点列表
    GETNODES(4);



    int type;
    MsgType(int type) {
        this.type = type;
    }
}
