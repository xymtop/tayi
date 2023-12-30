package com.xymtop.tayi.core.oprate.listener;

import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.pool.PoolItem;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 13:51
 */

@Data
public class OperateEvent {

    //事件类型
    private EventType type;

    //操作数据
    private PoolItem item;
}
