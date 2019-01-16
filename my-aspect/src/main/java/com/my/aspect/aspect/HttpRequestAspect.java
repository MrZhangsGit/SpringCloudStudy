package com.my.aspect.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/4
 */
@Aspect
@Slf4j
@Component
public class HttpRequestAspect {
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution( * com.my.aspect..*.*(..))")
    public void httpRequest() {}

    @Before("httpRequest()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        log.info("---:{}", Thread.currentThread().getStackTrace()[1].getMethodName());
        startTime.set(System.currentTimeMillis());

        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //记录请求内容
        log.info("URL : {}", JSON.toJSONString(request.getRequestURI()));
        log.info("HTTP_METHOD : {}", JSON.toJSONString(request.getMethod()));
        log.info("IP : {}", request.getRemoteAddr());
        log.info("CLASS_METHOD : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "httpRequest()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : {}", JSON.toJSONString(ret));
        log.info("SPEND TIME : {}", (System.currentTimeMillis() - startTime.get()));
    }
}
