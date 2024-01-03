package com.xymtop.tayi.core.vm.ipfs;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 13:44
 */

@Configuration
public class IPFSBeanConfig {


    @Value("${tayi.vm.ipfs.host}")
    private String ipfsHost;

        @Bean
      public IPFS ipfs(){
            return new IPFS(ipfsHost);
        }
}
