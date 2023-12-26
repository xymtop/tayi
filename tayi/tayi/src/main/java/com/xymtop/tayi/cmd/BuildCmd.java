package com.xymtop.tayi.cmd;

import com.xymtop.tayi.cmd.cmdbuilder.CmdArgsBuilder;
import com.xymtop.tayi.core.oprate.OperateEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 21:29
 */

//构建可执行指令

@Component
@Slf4j
public class BuildCmd {

    @Autowired
    private CmdSystem cmdSystem;


    @Autowired
    ApplicationContext applicationContext;


    @Autowired
    private CmdArgsBuilder cmdArgsBuilder;

    //构建可执行指令
    public ExecResult buildAndRun(OperateEntity operate) throws Exception {
        ExecResult result = new ExecResult();


        //获取到执行的指令
        String operateCmd = operate.getOperateCmd();




        //获取指令类型
        CmdMap cmdContent = cmdSystem.getCmdContent(operateCmd);





        if (cmdContent == null){
            throw new Exception("指令不存在");
        }

        //补充结果类型
        result.setCmd(operateCmd);

        //判断是否需要参数
        if (cmdContent.isNeedArgs()){
            //需要参数
            Object[] args = buildArgs(operateCmd,operate);
            result.setArgs(args);
            //执行指令并获取执行结果
            result.setResult(cmdSystem.exec(operateCmd,args));
            result.setResultFlag(true);

        }else {
            //不需要参数,直接执行指令
            result.setResult(cmdSystem.exec(operateCmd));
            result.setResultFlag(true);
        }
        log.info(result.toString());

        //构建结果返回

        return result;
    }

    //构建参数
    public Object[] buildArgs(String cmd,OperateEntity operate) throws InvocationTargetException, IllegalAccessException {
        //获取参数构建器
      return   cmdArgsBuilder.buildArgs(cmd,operate);
    }
}
