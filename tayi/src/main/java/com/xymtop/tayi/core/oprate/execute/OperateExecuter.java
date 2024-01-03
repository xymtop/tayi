package com.xymtop.tayi.core.oprate.execute;

import com.xymtop.tayi.core.cmd.BuildCmd;
import com.xymtop.tayi.core.cmd.ExecResult;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.OperateReceipt;
import com.xymtop.tayi.core.oprate.OperateStatus;
import com.xymtop.tayi.core.utils.jsonutils.XJsonUtils;
import com.xymtop.tayi.core.store.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 14:49
 */

@Component
public class OperateExecuter {

    @Autowired
    private BuildCmd buildCmd;

    @Autowired
    private DBUtils dbUtils;

    @Autowired
    XJsonUtils xJsonUtils;

    //执行元操作并返回收据
    public OperateReceipt execute(OperateEntity entity) throws Exception {
        OperateReceipt receipt = new OperateReceipt();

        //转到cmd系统执行操作并获取执行的结果
        ExecResult result =  buildCmd.buildAndRun(entity);


        //设置执行结果
        receipt.setStatus(result.isResultFlag()? OperateStatus.SUCCESS :OperateStatus.FAIL);

        //添加执行结果到收据中
        entity.setOperateReceipt(receipt);

        //添加执行结果到操作体中
        entity.setExecResult(result);

        //写入数据库
        dbUtils.put(entity.getOperateHash(),xJsonUtils.objToJson(entity));


        return receipt;
    }
}
