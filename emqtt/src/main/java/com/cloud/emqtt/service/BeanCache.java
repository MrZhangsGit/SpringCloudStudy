package com.cloud.emqtt.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @module: com.cloud.emqtt.service
 * @author: chenfei
 * @date: 2018 2018/12/26
 * @version: v1.0
 */
@Component
public class BeanCache implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DictService dictService = applicationContext.getBean(DictService.class);
        System.out.println("load bean...........................");
        dictService.sayHello();
    }
}
