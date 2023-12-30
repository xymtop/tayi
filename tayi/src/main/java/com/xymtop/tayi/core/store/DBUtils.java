package com.xymtop.tayi.core.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 3:12
 */

@Component
public class DBUtils {

    @Autowired
    private RocksDBUtils rocksDBUtils;


    public void put(String key, String value) throws Exception {
        rocksDBUtils.put(key, value);
    }

    public String get(String key) throws Exception {
        return rocksDBUtils.get(key);
    }

}
