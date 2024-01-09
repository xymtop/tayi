package com.xymtop.tayi.core.vm.contract;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/10 1:20
 */

@Data
public class Event {

    String name;

    String[]  value;

    public Event(String name,String... value){
        this.name = name;
        this.value = value;
    }
}
