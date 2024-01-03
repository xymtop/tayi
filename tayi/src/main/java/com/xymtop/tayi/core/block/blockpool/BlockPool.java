package com.xymtop.tayi.core.block.blockpool;

import com.xymtop.tayi.core.block.entity.Block;

import java.util.HashMap;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 20:24
 */


public class BlockPool {

    private static HashMap<String, Block> blockPool = new HashMap<>();


    //新增
    public  static synchronized void add(Block value){
        blockPool.put(value.getHash(),value);
    }


    //获取
    public static  synchronized  Block get(String key){
        return blockPool.get(key);
    }
}
