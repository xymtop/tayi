package com.xymtop.tayi.core.vm.virtual.object;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.utils.fileutils.FileUtils;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;


import java.io.*;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 15:51
 */
public class ObjectStreamUtils {

    // 序列化
    public static void serialize(String serPath,Object pro) throws IOException {

        String resourcesFilePath = FileUtils.getResourcesFilePath(serPath);
        if (resourcesFilePath == null){
            resourcesFilePath = serPath;
        }

        if (!FileUtil.exist(resourcesFilePath)){
            FileUtil.newFile(resourcesFilePath);
        }
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(resourcesFilePath));
        String jsonStr = JSONUtil.toJsonStr(pro);

        FileUtil.writeUtf8String(jsonStr,resourcesFilePath);

//        out.writeObject();
//        out.close();
    }



    public  static Object deserialize(String serPath) throws IOException, ClassNotFoundException {

        String resourcesFilePath = FileUtils.getResourcesFilePath(serPath);
        if (resourcesFilePath == null){
            resourcesFilePath = serPath;
        }

        //读文件
        String string = FileUtil.readUtf8String(new File(resourcesFilePath));

//        JSONObject parseObj = JSONUtil.parseObj(string);
        JSONObject jsonObject = JSONUtil.parseObj(string);

// 将JSONObject转换回原始对象
        Object newObj = JSONUtil.toBean(jsonObject, Object.class);


        return newObj;
    }


}
