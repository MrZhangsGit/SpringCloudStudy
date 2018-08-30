package com.my.filter.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zhangs
 */
@Configuration
public class FilterConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * addPathPatterns 用于添加拦截规则
         * excludePathPatterns 用户排除拦截
         */
        /**
         * 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
         */
        InterceptorRegistration addInterceptor = registry.addInterceptor(new FilterHandlerInterceptor());
        /**
         * 拦截路径
         */
        addInterceptor.addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
