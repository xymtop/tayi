package com.xymtop.tayi.core.ai.aioperate.runner.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/7 2:18
 */

@Data
public class RunResult {

    //是否运行成功
    boolean flag;

    //运行结果
    List<String> result;


    //运行的错误
    Exception exception;

}
