package com.xymtop.tayi.core.ai.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/7 2:42
 */

@Data
@Component
public class AiConfig {

    @Value("${ai.processPath}")
    private String processPath;
}
