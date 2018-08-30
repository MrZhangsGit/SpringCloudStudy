package com.my.mqtt.consumer.service;

/**
 * <desc>
 * EMQ服务管理接口。
 * </desc>
 *
 * @author zhangs
 * @createDate 2018/04/09
 */
public interface IEmqService {
    /**
     * 发布消息
     *
     * @param topic
     * @param content
     * @return
     */
    Boolean publish(String topic, String content);

    /**
     * 订阅消息
     *
     * @param topic
     * @return
     */
    Boolean subscribe(String topic);
}
