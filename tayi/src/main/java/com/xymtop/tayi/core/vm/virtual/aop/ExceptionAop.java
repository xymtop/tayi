package com.xymtop.tayi.core.vm.virtual.aop;

import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.poll.OpratePool;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

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

    @Autowired
    private OpratePool opratePool;

    // 定义切点，拦截某个接口所有方法
    @Pointcut("execution(com.xymtop.tayi.core.oprate.execute.OperateExecuter *.execute(..))")
    public void exception() {}

    // 在接口方法执行前执行
    @Around("exception()")
    public Object thatAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            //获取到参数
            Object[] args = joinPoint.getArgs();
            OperateEntity operate =     (OperateEntity) args[0];

            //获取到session
            WebSocketSession session = opratePool.get(operate.getOperateHash());

            if (session!=null){
                session.sendMessage(new TextMessage(throwable.getMessage()));
            }

            throw throwable;
        }
    }
}
