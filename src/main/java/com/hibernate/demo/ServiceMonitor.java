package com.hibernate.demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;

/**
 * @author wanli zhou
 * @created 2017-12-14 10:33 PM.
 */
//@Aspect
//@Component
public class ServiceMonitor {
    @Autowired
    private GaugeService gaugeService;
    @Around("execution(* com.hibernate.demo..*.*(..))")
    public void latencyService(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        pjp.proceed();
        long end = System.currentTimeMillis();
        gaugeService.submit(pjp.getSignature().toString(), end - start);
    }

    @Autowired
    private CounterService counterService;
    @Before("execution(* com.hibernate.demo..*.*(..))")
    public void countServiceInvoke(JoinPoint joinPoint) {
        counterService.increment(joinPoint.getSignature() + "");
    }
}