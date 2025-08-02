package com.example.dev.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.dev.controller.*.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        LOGGER.error("Executing method: {}", joinPoint.getSignature().getName());
    }

}
