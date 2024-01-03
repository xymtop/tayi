package com.xymtop.tayi.core.utils.apputils;

import lombok.Data;
import org.springframework.context.ApplicationContext;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 20:38
 */


public class AppUtils {

   static ApplicationContext applicationContext;


   public static ApplicationContext getApplicationContext() {
       return  applicationContext;
   }

   public static  void setApplicationContext(ApplicationContext app) {
     applicationContext = app;
   }
}
