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

    /**
     * 从指定 JAR 文件中加载类。
     *
     * @param jarFilePath JAR 文件的路径。
     * @param className   要加载的类的完全限定名。
     * @return 加载的类的 Class 对象。
     * @throws Exception 如果无法加载类或文件不可读。
     */
    public static Class<?> loadClassFromJar(String jarFilePath, String className) throws Exception {
        jarFilePath = FileUtils.getResourcesFilePath(jarFilePath);
        File jarFile = new File(jarFilePath);
        URL[] urls = { new URL("jar:file:" + jarFile.getAbsolutePath() + "!/") };
        try (URLClassLoader classLoader = new URLClassLoader(urls)) {
            return classLoader.loadClass(className);
        }
    }

}
