package com.example.ss13.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class  LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.ss13.controller..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("[AOP @Before] Calling method: {}", joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.example.ss13.service..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("[AOP @AfterReturning] Method: {} | Returned: {}", 
                joinPoint.getSignature().toShortString(), 
                result != null ? result.toString() : "null");
    }

    @Around("execution(* com.example.ss13.controller..*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            logger.info("[AOP @Around] Method: {} took {} ms to execute", 
                    joinPoint.getSignature().toShortString(), duration);
        }
    }
}
