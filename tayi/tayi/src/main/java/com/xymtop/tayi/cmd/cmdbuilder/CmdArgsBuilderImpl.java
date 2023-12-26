package com.xymtop.tayi.cmd.cmdbuilder;

import com.xymtop.tayi.cmd.CmdMap;
import com.xymtop.tayi.cmd.CmdSystem;
import com.xymtop.tayi.core.oprate.OperateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 21:51
 */

@Component
public class CmdArgsBuilderImpl implements CmdArgsBuilder{

    @Autowired
    private CmdSystem cmdSystem;

    @Override
    public Object[] buildArgs(String cmd,OperateEntity operate) throws InvocationTargetException, IllegalAccessException {
        CmdMap cmdContent = cmdSystem.getCmdContent(cmd);
        Method argsBuilder = cmdContent.getArgsBuilder();
        //执行对象
        Object obj = cmdContent.getObj();
        return (Object[]) argsBuilder.invoke(obj, operate);
    }
}
