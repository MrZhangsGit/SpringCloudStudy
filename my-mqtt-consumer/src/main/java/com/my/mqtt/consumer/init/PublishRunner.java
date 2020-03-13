package com.my.mqtt.consumer.init;

import com.my.mqtt.consumer.config.mqttWapper.PublishConn;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Auther: zhangs
 * @Date: 2018/11/16
 * @Description: 在spring容器启动后初始化发布链接
 */
@Slf4j
@Component
@Order(value = 3)
public class PublishRunner implements ApplicationRunner {
    @Autowired
    private PublishConn publishConn;
    private MqttClient mqttPubClient;

    public MqttClient getMqttPubClient() {
        return mqttPubClient;
    }

    public void setMqttPubClient(MqttClient mqttPubClient) {
        this.mqttPubClient = mqttPubClient;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        mqttPubClient = publishConn.getConn();
    }
}
