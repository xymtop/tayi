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

    //模型哈希
    private String hash;

    //模型配置路径
    private String configPath;

}
