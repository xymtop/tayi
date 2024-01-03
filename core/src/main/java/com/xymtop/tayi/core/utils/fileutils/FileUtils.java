package com.xymtop.tayi.core.utils.fileutils;

import cn.hutool.core.io.resource.ResourceUtil;
import org.apache.lucene.util.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;


/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 3:45
 */
public class FileUtils {


//    转换路径为当前资源目录路径
    public static String getResourcesFilePath(String path) throws IOException {
//        URL url = ResourceUtil.getResource(path);
//        if (url == null){
            File file = new File(path);
            String filePath = file.getAbsolutePath();
            String decodedPath = URLDecoder.decode(filePath, StandardCharsets.UTF_8.name());
            return decodedPath;
//        }
//        String filePath = url.getPath();
//        String decodedPath = URLDecoder.decode(filePath, StandardCharsets.UTF_8.name());
//        return decodedPath;
    }


}
