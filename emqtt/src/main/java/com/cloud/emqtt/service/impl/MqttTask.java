package com.cloud.emqtt.service.impl;

import com.cloud.emqtt.service.MqttProcesser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @module: com.berl.emqtt.service.impl
 * @author: chenfei
 * @date: 2018 2018/12/11
 * @version: v1.0
 */
public class MqttTask implements Runnable {
    public static AtomicInteger count = new AtomicInteger(0);
    private MqttProcesser mqttProcesser;
    private String topic;
    private ByteBuffer payload;
    private int qos;
    private boolean retain;
    private int taskid;
    private Logger logger = LoggerFactory.getLogger(MqttTask.class);

    public MqttTask(MqttProcesser mqttProcesser, String topic, byte[] payload, int qos, boolean retain) {
        this.mqttProcesser = mqttProcesser;
        this.topic = topic;
        ByteBuffer.wrap(payload);
        this.qos = qos;
        this.retain = retain;
        this.taskid = count.incrementAndGet();
    }

    @Override
    public void run() {
        if (!mqttProcesser.process(topic, payload, qos, retain))
            logger.warn("process[" + taskid + "] topic[" + topic + "] hand failded, please check your apatee...");
        else {
            logger.info("process[" + taskid + "] topic[" + topic + "] hand success");
        }
        logger.info("=======================================");
    }
}
