package com.cloud.emqtt.service.impl;

import com.cloud.emqtt.service.Mqtt;
import com.cloud.emqtt.service.MqttManager;
import com.cloud.emqtt.service.MqttWrapper;
import com.cloud.emqtt.service.ThreadPool;
import lombok.extern.slf4j.Slf4j;

/**
 * @module: com.cloud.emqtt.service.impl
 * @author: chenfei
 * @date: 2018 2018/12/10
 * @version: v1.0
 */
@Slf4j
public class MqttSubscrible extends Mqtt {
    public MqttSubscrible(ThreadPool threadPool, MqttManager mqttManager, MqttWrapper mqttWrapper) {
        super(threadPool, mqttManager, mqttWrapper);
    }

    @Override
    public boolean publish(String topic, byte[] payload, int qos, boolean retained) {
        return false;
    }

    @Override
    public boolean subscribe(final String topicName, int qos) {
        return mqttWrapper.subscribe(topicName, qos);
    }

    @Override
    public void unsubscribe(final String topicName) {
        mqttWrapper.unsubscribe(topicName);
    }

    @Override
    public void on_connect(boolean bStatus) {
        //TODO
        log.info("on_connect");
    }

    @Override
    public void on_disconnect(boolean bStatus) {
        //TODO
        log.info("on_disconnect");
    }

    @Override
    public void on_publish(int mid) {
        //TODO
        log.info("on_publish");
    }

    @Override
    public void on_subscribe(int mid) {
        //TODO
        log.info("on_subscribe");
    }

    @Override
    public void on_unsubscribe(int mid) {
        //TODO
        log.info("on_unsubscribe");
    }

    @Override
    public void on_message(String topic, byte[] payload, int qos, boolean retain) {
        Runnable task = new MqttTask(mqttManager.getMqttProcesser(), topic, payload, qos, retain);
        if (null != task) {
            threadPool.commint(task);
        } else {
            System.out.println("construct MqttTask failed, please check your memory!!!");
        }
    }
}
