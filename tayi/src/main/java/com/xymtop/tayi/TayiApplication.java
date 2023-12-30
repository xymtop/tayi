package com.xymtop.tayi;



import com.xymtop.tayi.core.system.SystemStatus;
import com.xymtop.tayi.core.vm.TaYiVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


//这是运行的入口
@SpringBootApplication
@EnableScheduling
public class TayiApplication   {

    public static void main(String[] args) {
        SpringApplication.run(TayiApplication.class);
    }

}
