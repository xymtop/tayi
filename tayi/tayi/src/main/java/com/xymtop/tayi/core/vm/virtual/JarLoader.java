package com.xymtop.tayi.core.vm.virtual;

import com.xymtop.tayi.core.utils.fileutils.FileUtils;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 18:15
 */
public class JarLoader {
    public static List<Object> loadImplementations(Class<?> interfaceClass, String url) throws Exception {
        url = FileUtils.getResourcesFilePath(url);
        File jarFile = new File(url);
        URL[] urls = { new URL("file:///"+url) };

        URLClassLoader classLoader = URLClassLoader.newInstance(urls);

        List<Object> instances = new ArrayList<>();
        try (JarFile jar = new JarFile(jarFile)) {
            jar.stream().forEach(jarEntry -> {
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName().replace('/', '.').replace(".class", "");
                    try {
                        Class<?> cls = classLoader.loadClass(className);
                        if (interfaceClass.isAssignableFrom(cls) && !cls.isInterface()) {
                            instances.add(cls.getDeclaredConstructor().newInstance());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return instances;
    }
}
