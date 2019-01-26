package com.oeasycloud.myeurekaclient.service.impl;

import com.oeasycloud.myeurekaclient.service.IService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/24
 */
@Service
public class ServiceImpl implements IService, InitializingBean {
    @Override
    public void sayHello() {
        System.out.println("sayHello---Hello!");
    }

    /**
     * 实现自InitializingBean接口。
     * 跟随项目启动而启动
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet---Hello!");
    }
}
