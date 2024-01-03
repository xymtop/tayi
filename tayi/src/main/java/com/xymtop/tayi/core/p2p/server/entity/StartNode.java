package com.xymtop.tayi.core.p2p.server.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 21:16
 */


@Component
@Data
public class StartNode {
    @Value("${tayi.network.init.ip}")
    String  ip;

    @Value("${tayi.network.init.port}")
    int  port;
}
