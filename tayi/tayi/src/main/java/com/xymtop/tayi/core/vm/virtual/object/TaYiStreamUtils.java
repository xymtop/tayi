package com.xymtop.tayi.core.vm.virtual.object;

import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;

import java.io.IOException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 15:54
 */
public class TaYiStreamUtils {

        // 序列化
        public static void serialize(String serPath, TaYiJavaContract pro) throws IOException {
                ObjectStreamUtils.serialize(serPath,pro);

        }

        public  static Object deserialize(String serPath) throws IOException, ClassNotFoundException {
                Object object = ObjectStreamUtils.deserialize(serPath);
                return object;
        }
}
