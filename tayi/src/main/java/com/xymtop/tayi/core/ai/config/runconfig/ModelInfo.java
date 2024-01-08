package com.xymtop.tayi.core.ai.config.runconfig;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/7 3:24
 */

@Data
public class ModelInfo {

    //名称
    private String name;

    //模型版本
    private String version;

    //模型哈希
    private String hash;

    //模型运行配置路径
    private String runConfigPath;

    //模型训练配置路径
    private String trainConfigPath;

    //是否可以训练
    private boolean canTrain;

}
