package com.xymtop.tayi.core.cmd.cmdbuilder;

import com.xymtop.tayi.core.oprate.OperateEntity;

import java.lang.reflect.InvocationTargetException;

public interface CmdArgsBuilder {

    Object[] buildArgs(String cmd,OperateEntity operate) throws InvocationTargetException, IllegalAccessException;
}
