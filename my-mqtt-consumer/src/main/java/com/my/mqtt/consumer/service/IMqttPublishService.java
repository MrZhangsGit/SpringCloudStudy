package com.my.mqtt.consumer.service;

/**
 * @author zhangs
 * @Description MQ发布消息
 * @createDate 2018/11/5
 */
public interface IMqttPublishService {
    /**
     * 发布消息
     *
     * @param topic
     * @param content
     * @return
     */
    Boolean publish(String topic, String content);
}
