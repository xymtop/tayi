package com.xymtop.tayi.core.utils.fileutils;

import java.net.URL;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 3:45
 */
public class FileUtils {

//    转换路径为当前资源目录路径
    public static String getResourcesFilePath(String path){
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL resource = classLoader.getResource("path");
       return  resource.getPath();
    }
}
