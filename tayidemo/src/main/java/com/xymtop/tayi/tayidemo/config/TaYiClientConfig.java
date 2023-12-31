package com.xymtop.tayi.tayidemo.config;

import com.xymtop.tayi.starttayi.TaYiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/31 23:39
 */

@Configuration
public class TaYiClientConfig {

    @Bean
    public TaYiClient taYiClient(){
        return new TaYiClient("ws://127.0.0.1:8081/rpc","0xjijisaiosuaiouhsghggjkdafg");
    }
}
