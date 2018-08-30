package com.my.mqtt.consumer.service.impl;

import com.my.mqtt.consumer.config.MqttConfiguration;
import com.my.mqtt.consumer.config.SubscribeConn;
import com.my.mqtt.consumer.service.IEmqService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableConfigurationProperties({MqttConfiguration.class})
public class IEmqServiceImpl implements IEmqService {

    @Autowired
    private MqttConfiguration mqttConfiguration;

    @Autowired
    private SubscribeConn subscribeConn;

    @Override
    public Boolean publish(String topic, String content) {
        return true;
    }

    @Override
    public Boolean subscribe(String topic) {
        log.info("MQ===subscribe=== 入参:topic:{}", topic);
        MqttClient mqttClient = subscribeConn.getMqttClient();
        // 判定是否需要重新连接
        if (mqttClient==null || !mqttClient.isConnected() || !mqttClient.getClientId().equals(mqttConfiguration.getSubscribeClientId())) {
            mqttClient = subscribeConn.getConn();
        }
        try {
            // 订阅消息
            int[] qos = {mqttConfiguration.getQos()};
            mqttClient.subscribe(new String[]{topic},qos);
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
