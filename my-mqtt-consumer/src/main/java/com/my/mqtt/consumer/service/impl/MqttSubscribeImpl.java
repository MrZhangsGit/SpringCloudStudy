package com.my.mqtt.consumer.service.impl;

import com.my.mqtt.consumer.config.mqttWapper.IMqttWrapperService;
import com.my.mqtt.consumer.service.IMqttSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MqttSubscribeImpl implements IMqttSubscribe {
    @Autowired
    private IMqttWrapperService iMqttWrapperService;

    @Override
    public Boolean subscribe(String topic) {
        return iMqttWrapperService.subscribe(topic);
    }
}
