package com.oeasycloud.myeurekaclient.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zhangs
 * @Description 注册到SpringBoot
 * @createDate 2019/4/9
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private FangshuaInterceptor interceptor;

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
