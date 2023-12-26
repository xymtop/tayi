package com.xymtop.tayi.core.oprate.execute;


import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.OperateReceipt;

//负责执行各个不同的操作
public interface Executer {

        //执行操作
        OperateReceipt execute(OperateEntity operateEntity);
}
