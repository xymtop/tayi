package com.xymtop.tayi.core.vm.virtual;

import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 14:16
 */


import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class JarRunner {
    public static void main(String[] args) throws Exception {
        URL jarUrl = new URL("file:///path/to/your/jarfile.jar");
        URLClassLoader childClassLoader = new URLClassLoader(new URL[]{jarUrl});
        Class<?> jarClass = Class.forName("YourMainClass", true, childClassLoader);
        Method mainMethod = jarClass.getDeclaredMethod("main", String[].class);
        String[] params = null; // 可以传递参数
        mainMethod.invoke(null, (Object) params);
    }
}
