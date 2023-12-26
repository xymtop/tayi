package com.xymtop.tayi.store;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 3:39
 */
@Configuration
public class BeanConfig {
    @Value("${db.filePath}")
    private String dbPath;

    @Bean
    public RocksDBUtils rocksDBUtils() throws Exception {
        RocksDBUtils rocksDBUtils = new RocksDBUtils();
        rocksDBUtils.init(dbPath);
        return rocksDBUtils;
    }
}
