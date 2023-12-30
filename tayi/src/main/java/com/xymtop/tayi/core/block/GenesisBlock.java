package com.xymtop.tayi.core.block;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.block.entity.BlockConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 4:04
 */
@Component
public class GenesisBlock {
    @Autowired
    BlockConfig blockConfig;

    public Block loadGenesisBlock() {
        String blockStr = FileUtil.readString(blockConfig.getGenesisBlockPath(), "utf-8");
        JSONObject json = JSONUtil.parseObj(blockStr);
        Block block = JSONUtil.toBean(json, Block.class);
        return block;
    }
}
