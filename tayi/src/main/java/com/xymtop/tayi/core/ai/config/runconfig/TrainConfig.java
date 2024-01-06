package com.xymtop.tayi.core.ai.config.runconfig;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/7 3:37
 */

@Data
public class TrainConfig {

    //可训练的平台
    RunPlatform platform;

    //可运行的版本
    String version;

    //入口程序名称
    String mainProcess;
}
