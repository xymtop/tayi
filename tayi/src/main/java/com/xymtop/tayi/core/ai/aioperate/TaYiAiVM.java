package com.xymtop.tayi.core.ai.aioperate;

import cn.hutool.core.io.FileUtil;
import com.xymtop.tayi.core.ai.aioperate.runner.entity.RunParam;
import com.xymtop.tayi.core.ai.aioperate.runner.entity.RunResult;
import com.xymtop.tayi.core.ai.config.AiConfig;
import com.xymtop.tayi.core.ai.config.ConfigLoader;
import com.xymtop.tayi.core.ai.config.runconfig.ModelInfo;
import com.xymtop.tayi.core.ai.config.runconfig.RunConfig;
import com.xymtop.tayi.core.ai.proof.ProofTask;
import com.xymtop.tayi.core.ai.proof.Prover;
import com.xymtop.tayi.core.ai.proof.Verifier;
import com.xymtop.tayi.core.utils.fileutils.FileUtils;
import com.xymtop.tayi.core.vm.ipfs.IPFSUtils;
import com.xymtop.tayi.core.vm.zip.ZIPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/8 22:18
 */

@Component
public class TaYiAiVM {


    @Autowired
    IPFSUtils ipfsUtils;

    @Autowired
    AiConfig aiConfig;

    @Autowired
    Prover prover;

    @Autowired
    Verifier verifier;

    @Autowired
    ProofTask proofTask;


    //部署ai
    public Object deployAI(String aiHash) throws IOException {

        //下载的文件路径
        String downloadFilePath = aiConfig.getProcessPath() +"/"+ aiHash+"/tayi.model";

        String unzipPath = aiConfig.getProcessPath() +"/"+ aiHash+"/dir";

        //下载文件
        boolean downloaded = ipfsUtils.downloadFile(aiHash,downloadFilePath);

        if (downloaded){
            ZIPUtils zipUtils = new ZIPUtils(downloadFilePath);

            //解压文件
            zipUtils.extractZipFile(unzipPath);

            //获取到模型配置
            ConfigLoader configLoader = new ConfigLoader(unzipPath+"/info.json");

            ModelInfo modelInfo = configLoader.getModelInfo();

            modelInfo.setHash(aiHash);

            return modelInfo;
        }



        return new ModelInfo();
    }


    //训练ai
    public Object trainAI(String aiHash,String trainDataAddress){
        return aiHash;
    }


    //调用ai
    public Object callAI(String aiHash,String aiInput) throws IOException {

        //下载的文件路径
        String downloadFilePath = aiConfig.getProcessPath() +"/"+ aiHash+"/tayi.model";

        String unzipPath = aiConfig.getProcessPath() +"/"+ aiHash+"/dir";

        if (!FileUtil.exist(FileUtils.getResourcesFilePath(downloadFilePath))){
            //下载文件
            boolean downloaded = ipfsUtils.downloadFile(aiHash,downloadFilePath);

            if (!downloaded){
                throw  new IOException("下载失败");
            }

            ZIPUtils zipUtils = new ZIPUtils(downloadFilePath);

            //解压文件
            zipUtils.extractZipFile(unzipPath);


        }

        //获取到模型配置
        ConfigLoader configLoader = new ConfigLoader(unzipPath+"/info.json");

        ModelInfo modelInfo = configLoader.getModelInfo();
        RunConfig runConfig = configLoader.getRunConfig();

        //运行模型
        String modelRunPath = unzipPath + "/"+runConfig.getMainProcess();

        RunParam runParam = new RunParam(modelRunPath,unzipPath, Arrays.asList(aiInput));


        //这里应该怎么运行呢

        Prover.ProverResult proverResult =  runFun(runConfig,runParam);


        boolean verified = verifier.verify(proverResult);

        if (verified){
            return  (RunResult) proverResult.result;
        }

        RunResult runResult = new RunResult(false,null, new Exception("验证失败"));
        return  runResult;
    }

    //执行人工智能函数
    @Async
    public Prover.ProverResult runFun(RunConfig runConfig, RunParam runParam) throws IOException {
      return   prover.executeAndProve(runConfig,runParam);
    }


}
