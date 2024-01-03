package com.xymtop.tayi.core.block.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 4:05
 */
@Component
@Data
public class BlockConfig {

    //创世区块地址
    @Value("${block.genesisBlockPath}")
    private String genesisBlockPath;

    //是否同步区块
    @Value("${block.isSync}")
    private boolean isSync;

    //版本
    @Value("${block.version}")
    private String version;

    //难度目标
    @Value("${block.target}")
    private String target;
}
