package com.xymtop.tayi.cmd.apis.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//cmd指令
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CmdApi {

}
