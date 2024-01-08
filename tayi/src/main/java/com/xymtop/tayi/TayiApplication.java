package com.xymtop.tayi;



import com.xymtop.tayi.core.ai.proof.ZeroKnowledgeProofDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


//这是运行的入口
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class TayiApplication   {

    public static void main(String[] args) {
       SpringApplication.run(TayiApplication.class);
    }

}
