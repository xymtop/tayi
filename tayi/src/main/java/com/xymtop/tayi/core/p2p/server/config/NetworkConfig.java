package com.xymtop.tayi.core.p2p.server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 21:42
 */

@Component
@Data
public class NetworkConfig {

        @Value("${tayi.network.socket.port}")
        private int port;

}
