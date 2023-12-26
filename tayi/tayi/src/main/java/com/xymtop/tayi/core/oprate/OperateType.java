package com.xymtop.tayi.core.oprate;


import lombok.Getter;

//操作类型
@Getter
public enum OperateType {

    //新增NFT
    ADD("add"),

   //    修改
    UPDATE("update"),

    //查询
    QUERY("query"),

    ;


    private  String type;

    OperateType(String type) {
        this.type = type;
    }

}
