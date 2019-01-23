package com.my.mqtt.producer.service.impl;

import com.my.mqtt.producer.config.MqttConfiguration;
import com.my.mqtt.producer.config.PushCallback;
import com.my.mqtt.producer.service.IEmqService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableConfigurationProperties({MqttConfiguration.class})
public class IEmqServiceImpl implements IEmqService {

    @Autowired
    private MqttConfiguration mqttConfiguration;

    @Override
    public Boolean publish(String topic, String content) {
        log.info("MQ===public=== 入参:topic:{};content:{}", topic, content);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(mqttConfiguration.getQos());
        /**
         * Retained为true时MQ会保留最后一条发送的数据，当断开再次订阅即会接收到这最后一次的数据
         */
        message.setRetained(true);
        try {
            MqttClient mqttClient = this.connect(mqttConfiguration.getPublishClientId(), mqttConfiguration.getUsername(),
                    mqttConfiguration.getPassword());
            // 判定是否需要重新连接
            /*String clientId =  UUID.randomUUID().toString() +
                    "[" + InetAddress.getLocalHost().getHostAddress() + "]";*/
            if (mqttClient == null || !mqttClient.isConnected() || !mqttClient.getClientId().equals(mqttConfiguration.getPublishClientId())) {
                mqttClient = this.connect(mqttConfiguration.getPublishClientId(), mqttConfiguration.getUsername(),
                        mqttConfiguration.getPassword());
            }
            mqttClient.publish(topic, message);
            log.info("emq已发topic: {} - message: {}", topic, message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean subscribe(String topic) {
        return null;
    }

    public MqttClient connect(String clientId, String userName, String password) throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(mqttConfiguration.getConnectionTimeout());
        options.setKeepAliveInterval(mqttConfiguration.getKeepAliveInterval());

        MqttClient client = new MqttClient(mqttConfiguration.getHost(), clientId, persistence);
        client.setCallback(new PushCallback());
        client.connect(options);
        return client;
    }
}
