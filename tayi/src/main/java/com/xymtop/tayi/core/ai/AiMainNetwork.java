package com.xymtop.tayi.core.ai;

import com.xymtop.tayi.core.ai.aioperate.runner.RunnerUtils;
import com.xymtop.tayi.core.ai.aioperate.runner.entity.RunParam;
import com.xymtop.tayi.core.ai.aioperate.runner.entity.RunResult;
import com.xymtop.tayi.core.ai.config.ConfigLoader;
import com.xymtop.tayi.core.ai.proof.ZeroKnowledgeProofDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/7 2:08
 */

@SpringBootApplication
public class AiMainNetwork {

//    public static void main(String[] args) throws IOException {
////        ConfigurableApplicationContext run = SpringApplication.run(AiMainNetwork.class, args);
//
////        RunResult runResult = RunnerUtils.run(new RunParam("ai/test/testprocess.exe", "ai/test",
////                Arrays.asList("肖叶茂")));
////        System.out.println(runResult);
////        ConfigLoader configLoader = new ConfigLoader("ai/test/info.json");
////
////        System.out.println(configLoader);
//    }
}
