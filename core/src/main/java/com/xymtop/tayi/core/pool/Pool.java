package com.xymtop.tayi.core.pool;

import com.xymtop.tayi.core.oprate.listener.EventType;
import com.xymtop.tayi.core.oprate.listener.OperateEvent;
import com.xymtop.tayi.core.oprate.listener.PoolListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 5:18
 */

//操作池

@Component
public class Pool {

    private PriorityBlockingQueue<PoolItem> pool;
    @Autowired
    ApplicationContext applicationContext;


    public Pool() {
        this.pool = new PriorityBlockingQueue<>(10000, Comparator.comparingLong(PoolItem::getTimestamp).reversed());
    }
    public synchronized void addItem(PoolItem item) throws Exception {
        //新增
        Map<String, PoolListener> beansOfType = applicationContext.getBeansOfType(PoolListener.class);
        OperateEvent operateEvent = new OperateEvent();
        operateEvent.setItem(item);
        operateEvent.setType(EventType.ADD);
        for (PoolListener poolListener : beansOfType.values()) {
            poolListener.onTransaction(operateEvent);
        }
        pool.add(item);
    }

    public synchronized PoolItem getItem() {

        return pool.poll();
    }

    //获取池里面的元素个数
    public  synchronized  int getSize(){
        return pool.size();
    }


    public synchronized PoolItem getPeek(){
       return pool.peek();
    }


}
