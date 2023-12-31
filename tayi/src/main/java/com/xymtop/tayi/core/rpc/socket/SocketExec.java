package com.xymtop.tayi.core.rpc.socket;

import com.xymtop.tayi.browser.entity.Result;
import com.xymtop.tayi.core.oprate.execute.OperateEntrance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/31 21:07
 */

@Component
public class SocketExec {

    @Autowired
    private OperateEntrance operateEntrance;

    public Object executeTransaction(Object playLoad) throws Exception {


        //构建消息体
        String msg = String.valueOf(playLoad);


        //获取交易查询的哈希或者结果
        Object executeResult = operateEntrance.execute(msg);

        Result result = Result.okData(executeResult);

        return result;
    }
}
