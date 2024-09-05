package com.hsbc.incident.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RepositoryLoggingAspect {

    @Around("execution(* org.springframework.data.repository.Repository+.*(..))")
    public Object logRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Entering log
        log.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());

        Object result;
        try {
            result = joinPoint.proceed();  // execute target method
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            // Exiting log with execution time
            log.info("Exiting method: {}. Execution time: {} ms", joinPoint.getSignature(), duration);
        }

        return result;
    }
}
