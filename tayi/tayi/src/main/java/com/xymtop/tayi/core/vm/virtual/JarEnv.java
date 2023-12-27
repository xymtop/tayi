package com.xymtop.tayi.core.vm.virtual;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 14:40
 */
import com.xymtop.tayi.core.utils.fileutils.FileUtils;
import lombok.Data;

import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;


@Data
public class JarEnv {
    private Class<?> loadedClass;


    private Object instance;


    public JarEnv(String jarPath, Class clazz) throws Exception {
        jarPath = FileUtils.getResourcesFilePath(jarPath);
        List<Object> objectList = JarLoader.loadImplementations(clazz, jarPath);
        if(objectList.size() == 0) {
            throw new Exception("No implementation found");
        }

        this.instance = objectList.get(0);
        this.loadedClass = instance.getClass();
    }

    public Method[] getAllMethods() {
        return loadedClass.getDeclaredMethods();
    }

    public Field[] getAllFields() {
        return loadedClass.getDeclaredFields();
    }

    public Object callMethod(String methodName, Class<?>[] paramTypes, Object[] params) throws Exception {
        Method method = loadedClass.getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        Constructor<?> constructor = loadedClass.getConstructor();
        Object instance = constructor.newInstance();
        return method.invoke(instance, params);
    }

    public static void main(String[] args) throws Exception {

    }
}
