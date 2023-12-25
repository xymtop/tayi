package com.xymtop.tayi.core.oprate;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: 操作体
 * @date 2023/12/26 1:16
 */

@Data
public class OperateEntity {

    //操作编号
    private String operateId;


    //发起人
    private String operator;

    //发起时间
    private String operateTime;

    //操作元数据
    private OprateMeta oprateMeta;

}
