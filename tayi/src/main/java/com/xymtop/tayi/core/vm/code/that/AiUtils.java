package com.xymtop.tayi.core.vm.code.that;

import cn.hutool.core.io.FileUtil;
import com.xymtop.tayi.core.ai.aioperate.runner.RunnerUtils;
import com.xymtop.tayi.core.ai.aioperate.runner.entity.RunParam;
import com.xymtop.tayi.core.ai.aioperate.runner.entity.RunResult;
import com.xymtop.tayi.core.ai.config.ConfigLoader;
import com.xymtop.tayi.core.ai.config.runconfig.ModelInfo;
import com.xymtop.tayi.core.ai.config.runconfig.RunConfig;
import com.xymtop.tayi.core.utils.fileutils.FileUtils;
import com.xymtop.tayi.core.vm.zip.ZIPUtils;

import java.io.IOException;
import java.util.Arrays;

public interface AiUtils {
    //部署ai
     Object deployAI(String aiHash) throws IOException;

    //训练ai
     Object trainAI(String aiHash,String trainDataAddress);


    //调用ai
     Object callAI(String aiHash,String aiInput) throws IOException;


}
