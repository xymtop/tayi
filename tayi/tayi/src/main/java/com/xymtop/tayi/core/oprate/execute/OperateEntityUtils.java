package com.xymtop.tayi.core.oprate.execute;

import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.pool.PoolItem;
import com.xymtop.tayi.core.utils.encrypt.HashUtils;
import com.xymtop.tayi.core.utils.jsonutils.XJsonUtils;
import com.xymtop.tayi.store.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 22:59
 */

@Component
public class OperateEntityUtils {

    @Autowired
    private HashUtils hashUtils;


    @Autowired
    private DBUtils dbUtils;

    @Autowired
    private XJsonUtils xJsonUtils;


    //计算唯一的哈希值，作为id
    public String getHash(OperateEntity item) {
        long timestamp = item.getTimestamp();
        return hashUtils.hashHex(item.toString());
    }

    //通过哈希值查询交易的结果
    public OperateEntity getResult(String hash) throws Exception {
        return xJsonUtils.jsonToObj(dbUtils.get(hash),OperateEntity.class);
    }

}
