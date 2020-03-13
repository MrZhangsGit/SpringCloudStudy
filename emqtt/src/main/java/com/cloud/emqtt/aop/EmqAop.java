package com.cloud.emqtt.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @module: com.cloud.emqtt.aop
 * @author: chenfei
 * @date: 2018 2018/12/25
 * @version: v1.0
 */
@Aspect
@Order(1)
@Component
public class EmqAop {
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.cloud.emqtt.controller..*.*(..))")
    public void emqLog() {
    }

    @Before("emqLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        // 处理完请求，返回内容
        System.out.println("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

    @AfterReturning(returning = "ret", pointcut = "emqLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        System.out.println("RESPONSE : " + ret);
        System.out.println("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }
}
