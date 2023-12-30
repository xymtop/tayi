package com.xymtop.tayi.core.pool.builder;

import com.xymtop.tayi.core.inter.Builder;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.pool.Pool;
import com.xymtop.tayi.core.pool.PoolItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 5:40
 */

@Component
public class PoolItemBuilder implements Builder<OperateEntity, PoolItem> {

    @Autowired
    private Pool pool;

    @Override
    public PoolItem build(OperateEntity source) throws Exception {

        PoolItem poolItem = new PoolItem(source);
        //添加到等待池中
        pool.addItem(poolItem);

        return poolItem;
    }
}
