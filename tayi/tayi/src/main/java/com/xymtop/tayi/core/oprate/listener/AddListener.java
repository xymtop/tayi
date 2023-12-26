package com.xymtop.tayi.core.oprate.listener;

import com.xymtop.tayi.core.oprate.execute.ExecuterEntrance;
import com.xymtop.tayi.core.pool.Pool;
import com.xymtop.tayi.core.pool.PoolItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 13:55
 */
@Component
public class AddListener implements PoolListener{

    @Autowired
    private ExecuterEntrance executerEntrance;

    @Autowired
    private Pool pool;
    @Override
    public void onTransaction(OperateEvent event) throws Exception {


        //操作池新增
        if (event.getType() == EventType.ADD){

//            //获取池内个数
//            int size = pool.getSize();
//
//            //如果个数大于10，拿出交易打包
//            if (size>=10){
//                executerEntrance.startPackage(PackageType.NUM);
//            } else if (isOverTime(pool.getPeek())) {
//                executerEntrance.startPackage(PackageType.TIME);
//            }
        }
    }

    //判断最新操作的时间戳是否大于2秒
    private boolean isOverTime(PoolItem item){

        if (item == null){
            return false;
        }
         return System.currentTimeMillis()-item.getTimestamp()>200;
    }
}
