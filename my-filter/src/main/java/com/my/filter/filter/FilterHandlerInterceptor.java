package com.my.filter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * @author zhangs
 */
@Slf4j
public class FilterHandlerInterceptor implements HandlerInterceptor {
    private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {
        System.out.println("自定义拦截器...TODO");
        /**
         * 在请求处理之前进行调用（Controller方法调用之前）
         */
        long beginTime = System.currentTimeMillis();
        startTimeThreadLocal.set(beginTime);
        log.info("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime), httpServletRequest.getRequestURI());
        System.out.println(httpServletRequest.getParameterMap().toString());

        /**
         * 只有返回true才会继续向下执行，返回false取消当前请求
         */
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e)
            throws Exception {
        /**
         * 在整个请求结束之后被调用
         */
        long beginTime = startTimeThreadLocal.get();
        long endTime = System.currentTimeMillis();
        log.info("计时结束：{}  耗时：{}ms  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS").format(endTime),
                (endTime - beginTime), httpServletRequest.getRequestURI());
        startTimeThreadLocal.remove();
    }
}
