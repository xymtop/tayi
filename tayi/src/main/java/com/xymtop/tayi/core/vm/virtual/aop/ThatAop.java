package com.xymtop.tayi.core.vm.virtual.aop;

import com.xymtop.tayi.core.vm.TaYiVM;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 17:16
 */


@Aspect
@Component
public class ThatAop{

    @Autowired
    TaYiVM taYiVM;

    // 定义切点，拦截某个接口所有方法
    @Pointcut("execution(com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract *.getThat(..))")
    public void that() {}

    // 在接口方法执行前执行
    @Around("that()")
    public Object thatAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return taYiVM.getThat();
    }
}

