package com.challenge.w2m.superheros.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeTrackerAspect {

    private static final Logger logger = Logger.getLogger(TimeTrackerAspect.class);

    @Around("@annotation(com.challenge.w2m.superheros.aop.TimeTracker)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
        long end = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        String method = joinPoint.getSignature().getName();
        String text = stringBuilder.append(method)
                .append(" - processing time: ").append(end - start).append(" Miliseconds").toString();
        logger.info(text);
        return obj;
    }
}
