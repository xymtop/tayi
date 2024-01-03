package com.xymtop.tayi.core.graph;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 15:25
 */

@Configuration
public class GraphBeanConfig {

    @Value("${neo4j.path}")
    private String path;

    @Bean
    public Neo4jUtils neo4jUtils() throws IOException {
        return new Neo4jUtils(path);
    }

}
