package com.xymtop.tayi.rpc.rpc;

import com.xymtop.tayi.core.oprate.execute.OperateEntrance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

@Controller
public class RSocketRpc {

    @Autowired
    private OperateEntrance operateEntrance;

    @MessageMapping("/")
    Mono<String> requestResponse(String request) throws Exception {
        operateEntrance.execute(request);
        System.out.println("Received: " + request);
        return Mono.just("Received: " + request);
    }
}

