package com.example.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.*.controller.*.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        LOGGER.info("-- {} start --", joinPoint.getSignature().getName());
    }

    @Around("execution(* com.example.*.controller.*.*(..))")
    public Object spendTimeByController(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        LOGGER.info("-- {} spend {} millisecond --", joinPoint.getSignature().getName(), end - start);
        return proceed;
    }

    @After("execution(* com.example.*.controller.*.*(..))")
    public void logAfterController(JoinPoint joinPoint) {
        LOGGER.info("-- {} end --", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "execution(* com.example.*.controller.*.*(..))", throwing = "ex")
    public void logAfterThrowingController(JoinPoint joinPoint, Throwable ex) {
        LOGGER.error("-- {} throw exception--", joinPoint.getSignature().getName(), ex);
    }
}
