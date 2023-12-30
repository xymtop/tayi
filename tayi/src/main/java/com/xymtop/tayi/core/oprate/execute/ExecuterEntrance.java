package com.xymtop.tayi.core.oprate.execute;

import com.xymtop.tayi.core.block.blockpackage.Packager;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.OperateReceipt;
import com.xymtop.tayi.core.oprate.listener.PackageType;
import com.xymtop.tayi.core.pool.Pool;
import com.xymtop.tayi.core.pool.PoolItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 8:41
 */
//执行入口
@Component
public class ExecuterEntrance {

    @Autowired
    private Pool pool;

    @Autowired
    private Packager packager;

//    开始打包交易
    public  void startPackage(PackageType type) throws Exception {
        ArrayList<PoolItem> poolItems = new ArrayList<>();
        //如果是个数达标
        if (type==PackageType.NUM) {
            //获取池内的元素
            for (int i = 0; i < 10; i++){
                poolItems.add(pool.getItem());
            }

        } else if (type==PackageType.TIME) {
            if (pool.getSize()>0){
                for (int i = 0; i < pool.getSize(); i++){
                    poolItems.add(pool.getItem());
                }
            }

        }

        //打包
        packager.pack(poolItems);
    }
}
