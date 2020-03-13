package com.my.mqtt.consumer.service.impl;

import com.my.mqtt.consumer.config.mqttWapper.IMqttWrapperService;
import com.my.mqtt.consumer.service.IMqttPublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangs
 * @Description MQ发布信息
 * @createDate 2018/11/5
 */
@Service
@Slf4j
public class MqttPublishServiceImpl implements IMqttPublishService {
    @Autowired
    private IMqttWrapperService iMqttWrapperService;

    /**
     * 发布消息
     *
     * @param topic
     * @param content
     * @return
     */
    @Override
    public Boolean publish(String topic, String content) {
        return iMqttWrapperService.publish(topic, content);
    }
}
