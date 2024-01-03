package com.xymtop.tayi.core.oprate.listener;

import lombok.Getter;

@Getter
public enum EventType {

    //操作池新增
    ADD("add"),
    ;

    private final String type;

    EventType(String type) {
      this.type = type;
    }

}
