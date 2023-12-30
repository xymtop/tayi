package com.xymtop.tayi.core.pool;

import com.xymtop.tayi.core.oprate.OperateEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 5:20
 */

@Data
public class PoolItem {

//    操作
    private OperateEntity operateEntity;

//    操作接收时的时间戳
    private long timestamp;

    public PoolItem(OperateEntity operateEntity){
        this.operateEntity = operateEntity;
        this.timestamp = System.currentTimeMillis();
    }
}
