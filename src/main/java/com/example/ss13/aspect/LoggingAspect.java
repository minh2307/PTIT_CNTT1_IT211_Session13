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

/**
 * AOP Logging cho toàn bộ ứng dụng
 * Nhiệm vụ: Log request, thời gian thực thi và kết quả service
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Before Advice: Log tên method trong Controller trước khi thực thi
     */
    @Before("execution(* com.example.ss13.controller.*.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info(" [CONTROLLER] Method {} is being called", methodName);
    }

    /**
     * Around Advice: Đo thời gian thực thi của Controller
     */
    @Around("execution(* com.example.ss13.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;

        logger.info(" [PERFORMANCE] Method {} executed in {} ms",
                joinPoint.getSignature().getName(), executionTime);

        return result;
    }

    /**
     * AfterReturning Advice: Log kết quả trả về từ Service
     */
    @AfterReturning(pointcut = "execution(* com.example.ss13.service.*.*(..))",
            returning = "result")
    public void logAfterService(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.debug("[SERVICE] Method {} returned: {}", methodName, result);
    }
}
