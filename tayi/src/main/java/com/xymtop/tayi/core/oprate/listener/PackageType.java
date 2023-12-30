package com.xymtop.tayi.core.oprate.listener;

public enum PackageType {
    //数量达标
    NUM(1),

    //时间达标
    TIME(2);

    private  int type;
    PackageType(int type) {
        this.type = type;
    }
}
