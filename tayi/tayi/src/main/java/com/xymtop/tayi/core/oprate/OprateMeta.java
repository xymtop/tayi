package com.xymtop.tayi.core.oprate;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 2:23
 */

@Data
public class OprateMeta {

    //操作源对象
    private String sourceObject;

//    操作目标对象
    private String targetObject;

//    操作类型
    private OperateType operateType;

}
