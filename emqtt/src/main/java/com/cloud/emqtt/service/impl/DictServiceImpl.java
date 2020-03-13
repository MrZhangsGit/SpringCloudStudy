package com.cloud.emqtt.service.impl;

import com.cloud.emqtt.service.DictService;
import org.springframework.stereotype.Service;

/**
 * @module: com.cloud.emqtt.service.impl
 * @author: chenfei
 * @date: 2018 2018/12/26
 * @version: v1.0
 */
@Service
public class DictServiceImpl implements DictService {
    @Override
    public void sayHello() {
        System.out.println("Hello ApplicationContextAware");
    }
}
