package com.xymtop.tayi.core.ai.aioperate.runner;

import com.xymtop.tayi.core.ai.aioperate.runner.entity.RunParam;
import com.xymtop.tayi.core.ai.aioperate.runner.entity.RunResult;
import com.xymtop.tayi.core.utils.fileutils.FileUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/7 2:16
 */
public class RunnerUtils {

    public  static RunResult run(RunParam runParam){
        try {

            RunResult runResult = new RunResult();

            // 创建ProcessBuilder实例
            ProcessBuilder builder = new ProcessBuilder(FileUtils.getResourcesFilePath(runParam.getProcessPath()));
            builder.directory(new File(FileUtils.getResourcesFilePath(runParam.getWorkPath()))); // 设置工作目录
            //设置参数
            if (runParam.getArgs()!= null && runParam.getArgs().size() > 0){
                builder.command().addAll(runParam.getArgs());
            }

            builder.redirectErrorStream(true); // 合并标准输出和标准错误输出

            // 启动进程
            Process process = builder.start();

            // 读取输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));


            List<String> resLines = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {

                resLines.add(line);
            }

            // 等待进程结束
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                runResult.setFlag(true);
                runResult.setResult(resLines);
                return runResult;
            }else {
                runResult.setFlag(false);
                runResult.setResult(resLines);
                return runResult;
            }
        }catch (Exception e){

            RunResult runResult = new RunResult();
            runResult.setFlag(false);
            runResult.setException(e);

            return  runResult;
        }
    }
}
