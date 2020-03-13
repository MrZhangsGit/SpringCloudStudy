package com.cloud.emqtt.service.impl;

import com.cloud.emqtt.service.Mqtt;
import com.cloud.emqtt.service.MqttManager;
import com.cloud.emqtt.service.MqttWrapper;
import com.cloud.emqtt.service.ThreadPool;

/**
 * @module: com.cloud.emqtt.service.impl
 * @author: chenfei
 * @date: 2018 2018/12/10
 * @version: v1.0
 */
public class MqttPublish extends Mqtt {
    public MqttPublish(ThreadPool threadPool, MqttManager mqttManager, MqttWrapper mqttWrapper) {
        super(threadPool, mqttManager, mqttWrapper);
    }

    @Override
    public boolean publish(String topic, byte[] payload, int qos, boolean retained) {
        return mqttWrapper.publish(topic, payload, qos, retained);
    }

    @Override
    public void on_connect(boolean bStatus) {
        //TODO
    }

    @Override
    public void on_disconnect(boolean bStatus) {
        //TODO
    }

    @Override
    public void on_publish(int mid) {
        //TODO
    }

    @Override
    public void on_subscribe(int mid) {
        //TODO
    }

    @Override
    public void on_unsubscribe(int mid) {
        //TODO
    }
}
