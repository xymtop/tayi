package com.xymtop.tayi.cmd;

import com.xymtop.tayi.cmd.cmdbuilder.CmdArgsBuilder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 21:10
 */

@Data
public class CmdMap {
    //名称
    private String name;

    //实现类对象
    private Object obj;

   //    方法
   private Method method;

    //是否需要参数
    private boolean needArgs;

    //参数构建器
    private Method argsBuilder;

}
