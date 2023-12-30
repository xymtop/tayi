package com.xymtop.tayi.core.block.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 4:38
 */

@Component
@Data
public class BlockChain {
    //区块总数
    private long blockCount = 0;

}
