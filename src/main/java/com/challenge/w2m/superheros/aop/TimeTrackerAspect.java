package com.challenge.w2m.superheros.aop;

import com.challenge.w2m.superheros.util.FileUtils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeTrackerAspect {

    @Around("@annotation(com.challenge.w2m.superheros.aop.TimeTracker)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
        long end = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        String method = joinPoint.getSignature().getName();
        LocalDateTime now = LocalDateTime.now();
        String text = stringBuilder.append(method)
                .append(" - processing time: ").append(end - start).append(" Miliseconds - ")
                .append("Executed on: ").append(now).toString();
        try {
            FileUtils.saveToFile(text, this.getAnnotationParams(joinPoint));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private String getAnnotationParams(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        TimeTracker timeTracker = method.getAnnotation(TimeTracker.class);
        return timeTracker.value();
    }

}
