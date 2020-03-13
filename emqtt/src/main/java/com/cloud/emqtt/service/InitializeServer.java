package com.cloud.emqtt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @module: com.cloud.emqtt.service
 * @author: chenfei
 * @date: 2018 2018/12/11
 * @version: v1.0
 */
@Component
@Order(value = 1)
public class InitializeServer implements ApplicationRunner {

    @Autowired
    private MqttManager mqttManager;

    @Override
    public void run(ApplicationArguments args) {
        mqttManager.startup();
    }
}
