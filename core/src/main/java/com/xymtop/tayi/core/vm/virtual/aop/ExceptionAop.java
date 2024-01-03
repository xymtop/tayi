package com.xymtop.tayi.core.vm.virtual.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 18:32
 */
@Aspect
@Component
@Slf4j
public class ExceptionAop {
    // 定义切点，拦截某个接口所有方法
    @Pointcut("execution(com.xymtop.tayi.core.vm.TaYiVM *.*(..))")
    public void exception() {}

    // 在接口方法执行前执行
    @Around("exception()")
    public Object thatAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
           log.atWarn();
            throw throwable;
        }
    }
}
