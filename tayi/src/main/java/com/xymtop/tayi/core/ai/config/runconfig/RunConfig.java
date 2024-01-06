package com.xymtop.tayi.core.ai.config.runconfig;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/7 3:21
 */

@Data
public class RunConfig {

    //可运行平台
    RunPlatform platform;

    //可运行的版本
    String version;

    //入口程序名称
    String mainProcess;

    //是否需要参数
    boolean needArgs;

    //参数个数
    int argsCount;

    //参数类型
    ArgsType argsType;

}
