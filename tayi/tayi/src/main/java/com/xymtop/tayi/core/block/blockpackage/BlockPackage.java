package com.xymtop.tayi.core.block.blockpackage;

import com.xymtop.tayi.core.pool.PoolItem;

import java.util.List;

public interface BlockPackage {
    //打包
    void pack(List<PoolItem> items) throws Exception;

}
