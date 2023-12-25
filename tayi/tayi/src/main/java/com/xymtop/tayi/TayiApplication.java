package com.xymtop.tayi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

//这是运行的入口
@SpringBootApplication
public class TayiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TayiApplication.class, args);
    }

}
