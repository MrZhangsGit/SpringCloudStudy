package com.my.mqtt.consumer.config.mqttWapper.impl;

import com.alibaba.fastjson.JSON;
import com.my.mqtt.consumer.config.mqttWapper.*;
import com.my.mqtt.consumer.init.PublishRunner;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@EnableConfigurationProperties({MqttConfiguration.class})
public class IMqttWrapperServiceImpl implements IMqttWrapperService {

    @Autowired
    private MqttConfiguration mqttConfiguration;

    @Autowired
    private SubscribeConn subscribeConn;

    @Autowired
    private PublishConn publishConn;

    @Autowired
    private PublishRunner publishRunner;

    @Override
    public Boolean publish(String topic, String content) {
        log.info("MQ===public=== 入参:topic:{};content:{}", topic, content);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(mqttConfiguration.getQos());
        /**
         * Retained为true时MQ会保留最后一条发送的数据，当断开再次订阅即会接收到这最后一次的数据
         */
        message.setRetained(false);
        MqttClient mqttClient = publishRunner.getMqttPubClient();
        try {
            // 判定是否需要重新连接
            String clientId = UUID.randomUUID().toString() +
                    "[" + InetAddress.getLocalHost().getHostAddress() + "]";
            if (mqttClient==null || !mqttClient.isConnected()) {
                mqttClient = publishConn.getConn();
                publishRunner.setMqttPubClient(mqttClient);
                log.info("---mq重新连接---：{}",mqttClient.getClientId());
            }
            mqttClient.publish(topic, message);
            log.info("emq已发topic: {} - message: {}", topic, message);
        } catch (Exception e) {
            log.info("MQ===public=== 发布失败！::Error::{}", JSON.toJSONString(e));
            return false;
        }
        log.info("MQ===public=== 发布成功！:topic:{};content:{}", topic, content);
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
            log.info("IMqttWrapperServiceImpl---subscribe---mqttClient内存地址::{}", System.identityHashCode(mqttClient));
            mqttClient.subscribe(new String[]{topic},qos);
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 取消订阅消息
     * @param topic
     * @return
     */
    @Override
    public Boolean unSubscribe(String topic) {
        log.info("MQ===unSubscribe=== 入参:topic:{}", topic);
        MqttClient mqttClient = publishRunner.getMqttPubClient();
        // 判定是否需要重新连接
        if (mqttClient==null || !mqttClient.isConnected() || !mqttClient.getClientId().equals(mqttConfiguration.getSubscribeClientId())) {
            mqttClient = subscribeConn.getConn();
        }
        try {
            mqttClient.unsubscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
