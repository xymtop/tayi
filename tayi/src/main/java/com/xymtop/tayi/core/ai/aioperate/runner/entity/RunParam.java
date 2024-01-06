package com.xymtop.tayi.core.ai.aioperate.runner.entity;

import com.xymtop.tayi.core.utils.fileutils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/7 2:21
 */

@Data
@AllArgsConstructor
public class RunParam {

    //二进制文件里面
    String processPath;


    //工作目录
    String workPath;


    //参数
    List<String> args ;


}
