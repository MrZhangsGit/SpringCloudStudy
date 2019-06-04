package com.oeasycloud.myeurekaclient.filter;

import com.oeasycloud.myeurekaclient.annotation.AccessLimit;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author zhangs
 * @Description 防刷拦截器
 * @createDate 2019/4/9
 */
@Component
public class FangshuaInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            //获取方法中的注解，看是否有该注解
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean login = accessLimit.needLogin();
            String key = request.getRequestURI();

            //若需要登录，则获取登录的session进行判断
            if (login) {

            }

            //TODO 可从redis中获取用户访问的次数再进行判断
            //render(response);
            //return false;
        }

        return true;
    }

    private void render(HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream outputStream = response.getOutputStream();
        String string = "ACCESS_LIMIT_REACHED";
        outputStream.write(string.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
