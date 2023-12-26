package com.xymtop.tayi.cmd;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 21:32
 */

@Data
public class ExecResult {

    //执行参数
    private Object[] args;

    //执行指令
    private String cmd;

    //执行结果
    private Object result;

    //执行成功
    private boolean resultFlag;

}
