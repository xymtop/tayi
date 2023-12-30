package com.xymtop.tayi.core.oprate;

import lombok.Getter;

@Getter
public enum OperateStatus {

    //操作成功
    SUCCESS("0x1"),
    //操作失败
    FAIL("0x0"),
    ;

    OperateStatus(String status) {
         this.status = status;
    }

    private String status;
}
