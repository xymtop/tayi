package com.xymtop.tayi.core.oprate;


import lombok.Getter;

//操作类型
@Getter
public enum OperateType {

    //执行类操作，需要共识
    EXEC("exec"),



    //查询，不需要共识
    QUERY("query"),

    ;


    private  String type;

    OperateType(String type) {
        this.type = type;
    }

}
