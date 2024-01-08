package com.xymtop.tayi.core.ai.proof;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/9 0:33
 */
import com.xymtop.tayi.core.ai.aioperate.runner.RunnerUtils;
import com.xymtop.tayi.core.ai.aioperate.runner.entity.RunParam;
import com.xymtop.tayi.core.ai.aioperate.runner.entity.RunResult;
import com.xymtop.tayi.core.ai.config.runconfig.ArgsType;
import com.xymtop.tayi.core.ai.config.runconfig.RunConfig;
import com.xymtop.tayi.core.utils.fileutils.FileUtils;
import com.xymtop.tayi.core.vm.ipfs.IPFSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Component
public class HeavyComputation {

    @Autowired
    IPFSUtils ipfsUtils;
    public   RunResult exec(RunConfig runConfig, RunParam runParam) throws IOException {
       return   runModel(runConfig,runParam);
    }

    private RunResult runModel(RunConfig runConfig, RunParam runParam) throws IOException {
        //构建参数
        runParam =   buildArgs(runConfig,runParam);
        RunResult runResult = RunnerUtils.run(runParam);
        return  runResult;
    }

    private RunParam buildArgs(RunConfig runConfig,RunParam runParam) throws IOException {

        //判断是否需要参数
        if (runConfig.isNeedArgs()){

            ArgsType argsType = runConfig.getArgsType();

            if (argsType == ArgsType.FILE){
                //获取运行的目录
                String workPath = runParam.getWorkPath();
                //下载
                List<String> newArgs = new ArrayList<>();
                for (String fileHash : runParam.getArgs()){
                    boolean downloaded = ipfsUtils.downloadFile(fileHash, workPath + "/" + fileHash + ".tayiarg");
                    if (downloaded){
                        newArgs.add(FileUtils.getResourcesFilePath(workPath + "/" + fileHash + ".tayiarg"));
                    }
                }

                runParam.setArgs(newArgs);
            }

            if (argsType == ArgsType.TEXT){
                //不必做处理
            }

        }
        return  runParam;
    }
}
