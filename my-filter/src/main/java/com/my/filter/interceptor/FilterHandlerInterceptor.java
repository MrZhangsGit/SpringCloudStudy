package com.my.filter.interceptor;

import com.alibaba.fastjson.JSON;
import com.my.filter.util.HttpHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * @author zhangs
 */
@Slf4j
public class FilterHandlerInterceptor implements HandlerInterceptor {
    private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器
     * 是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
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
        /*System.out.println("1:" + httpServletRequest.getParameterMap().toString());
        System.out.println("2:" + HttpHelper.getRequestBody(httpServletRequest.getInputStream()));
        System.out.println("3:" + httpServletRequest.getSession().getAttribute("content"));
        System.out.println("4:" + httpServletRequest.getContentLength());
        //获取post请求form表单中的数据
        System.out.println("5:" + httpServletRequest.getParameter("content"));*/
        //获取post请求Body中的Json数据
        //System.out.println("6" + JSON.toJSONString(HttpHelper.getRequestBody(httpServletRequest.getInputStream())));
        boolean result = this.preventSQLInjection(httpServletRequest);
        if (!result) {
            return false;
        }

        /**
         * 只有返回true才会继续向下执行，返回false取消当前请求
         */
        return true;
    }

    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，
     * 它的执行时间是在处理器进行处理之后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的
     * 渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操作。这个方法的链式结构跟正常访问的方向是相反的，也就
     * 是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，只是Struts2里面
     * 的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一
     * 个Interceptor或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的
     * 内容都写在调用invoke方法之后。
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就
     * 是DispatcherServlet渲染了视图执行，这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor
     * 的preHandle方法的返回值为true时才会执行。
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
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

    /**
     * 校验SQL注入是否通过
     * @param request
     * @return
     * @author zhangs
     * @createDate 2019/01/04
     */
    private boolean preventSQLInjection(HttpServletRequest request) {
        try {
            InputStream inputStream = request.getInputStream();
            String body = HttpHelper.getRequestBody(inputStream);
            log.info("{}::校验SQL注入是否通过 Body::{}", Thread.currentThread().getStackTrace()[1].getMethodName() ,
                    JSON.toJSONString(body));
            return preventSQLInjection(body);
        } catch (Exception e) {
            log.info("{}::校验SQL注入是否通过出错 ERROR::{}", Thread.currentThread().getStackTrace()[1].getMethodName() ,
                    JSON.toJSONString(e));
        }
        return false;
    }

    /**
     * 校验SQL注入是否通过
     * @param content 检测内容
     * @return
     * @author zhangs
     * @createDate 2019/01/04
     */
    public static boolean preventSQLInjection(String content) {
        if (StringUtils.isEmpty(content)) {
            return true;
        }
        String regular = ".*([';]+|(--)+).*";

        int beforeLength = content.length();
        int afterLength = content.replaceAll(regular, "").length();
        if (beforeLength == afterLength) {
            return true;
        }
        return false;
    }
}
