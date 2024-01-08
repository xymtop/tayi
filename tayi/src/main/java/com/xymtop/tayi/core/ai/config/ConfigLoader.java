package com.xymtop.tayi.core.ai.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.ai.config.runconfig.ModelInfo;
import com.xymtop.tayi.core.ai.config.runconfig.RunConfig;
import com.xymtop.tayi.core.ai.config.runconfig.TrainConfig;
import com.xymtop.tayi.core.utils.fileutils.FileUtils;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/8 17:56
 */

@Data
public class ConfigLoader {
    //总配置信息
    ModelInfo modelInfo;

    RunConfig runConfig;

    TrainConfig trainConfig;
    public ConfigLoader(String configPath) throws IOException {
        File jsonFile = new File(FileUtils.getResourcesFilePath(configPath));
        //获取文件的文件夹路径
        String parentPath = jsonFile.getParent();

        JSONObject jsonObject = JSONUtil.readJSONObject(jsonFile, Charset.defaultCharset());

        modelInfo =  JSONUtil.toBean(jsonObject,ModelInfo.class,false);

        if (modelInfo != null){
            String runConfigPath = modelInfo.getRunConfigPath();
            String trainConfigPath = modelInfo.getTrainConfigPath();

            if (runConfigPath != null){
                runConfig = JSONUtil.toBean(JSONUtil.readJSONObject(new File(FileUtils.getResourcesFilePath(parentPath+"/"+runConfigPath)),Charset.defaultCharset()),RunConfig.class,false);
            }


            if (trainConfigPath != null){
                trainConfig = JSONUtil.toBean(JSONUtil.readJSONObject(new File(FileUtils.getResourcesFilePath(parentPath+"/"+trainConfigPath)),Charset.defaultCharset()),TrainConfig.class,false);
            }
        }

    }

}
