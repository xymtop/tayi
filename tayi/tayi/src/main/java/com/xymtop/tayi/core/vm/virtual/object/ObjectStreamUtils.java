package com.xymtop.tayi.core.vm.virtual.object;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.utils.fileutils.FileUtils;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import com.xymtop.tayi.core.vm.virtual.JarEnv;


import java.io.*;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 15:51
 */
public class ObjectStreamUtils {

    // 序列化
    public static void serialize(String serPath,Object pro) throws IOException, IllegalAccessException {

        String resourcesFilePath = FileUtils.getResourcesFilePath(serPath);
        if (resourcesFilePath == null){
            resourcesFilePath = serPath;
        }

        if (!FileUtil.exist(resourcesFilePath)){
            FileUtil.newFile(resourcesFilePath);
        }
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(resourcesFilePath));
//        String jsonStr = JSONUtil.toJsonStr(pro);

        JSONObject jsonObject = beanToJsonObj(pro);

        String jsonStr = JSONUtil.toJsonStr(jsonObject);

        FileUtil.writeUtf8String(jsonStr,resourcesFilePath);

//        out.writeObject();
//        out.close();
    }



    public  static Object deserialize(String filePath,String serPath) throws Exception {

        String resourcesFilePath = FileUtils.getResourcesFilePath(serPath);
        if (resourcesFilePath == null){
            resourcesFilePath = serPath;
        }

        //读文件
        String str = FileUtil.readUtf8String(new File(resourcesFilePath));


        JSONObject jsonObject = JSONUtil.parseObj(str);


// 将JSONObject转换回原始对象
       Object   object = jsonObjectToBean(filePath,jsonObject);


        return object;
    }


    //beanToJsonObj
    private static JSONObject beanToJsonObj(Object object) throws IllegalAccessException {
        JSONObject jsonObject = new JSONObject();

        Class<?> aClass = object.getClass();



        for (Field field : aClass.getDeclaredFields()){

            field.setAccessible(true);

            //读取值
            Object fieldValue = field.get(object);

            if (!ObjectUtil.isNull(fieldValue)){

                jsonObject.putOpt(field.getName(),fieldValue);

            }

        }

        return jsonObject;
    }


    public static  Object jsonObjectToBean(String jarPath,JSONObject jsonObject) throws Exception {


        JarEnv jarEnv = new JarEnv(jarPath,TaYiJavaContract.class);

        Object object = jarEnv.getInstance();


        Set<String> keySet = jsonObject.keySet();

        Class<?> aClass = object.getClass();


        for (String key : keySet){

            //设置值
            Field field = aClass.getDeclaredField(key);

            if (field != null){

                field.setAccessible(true);

                field.set(object,jsonObject.get(key));
            }

        }

        return  object;
    }

}
