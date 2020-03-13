package com.cloud.emqtt.service;

import com.cloud.emqtt.config.MqttConfigure;
import com.cloud.emqtt.service.impl.MqttMsgHandler;
import com.cloud.emqtt.service.impl.MqttPublish;
import com.cloud.emqtt.service.impl.MqttSubscrible;
import com.cloud.emqtt.service.impl.emqtt.EmqttWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @module: com.berl.emqtt.service
 * @author: chenfei
 * @date: 2018 2018/12/10
 * @version: v1.0
 */
@Component  // 关键1
@EnableConfigurationProperties(MqttConfigure.class)
public class MqttManager {
    private final AtomicLong sequenceNumber = new AtomicLong(0);
    @Autowired
    private MqttConfigure mqttConfigure;
    @Autowired
    private ThreadPool threadPool;
    private Logger logger = LoggerFactory.getLogger(MqttManager.class);
    private ArrayList<Mqtt> subscribeArrayList = new ArrayList<>();
    private ArrayList<Mqtt> publishArrayList = new ArrayList<>();
    private MqttProcesser mqttProcesser;

    public MqttManager() {
        mqttProcesser = new MqttMsgHandler();
    }

    public static String GetGUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public MqttProcesser getMqttProcesser() {
        return mqttProcesser;
    }

    public Mqtt grab2pub() {
        int index = (int) sequenceNumber.incrementAndGet();
        return publishArrayList.get(index % (publishArrayList.size()));
    }

    public Mqtt grab2Sub() {
        int index = (int) sequenceNumber.incrementAndGet();
        return subscribeArrayList.get(index % (subscribeArrayList.size()));
    }

    public boolean startup() {
        logger.info(mqttConfigure.toString());
        if (initPublish() && initSubscribe()) {
            for (int i = 0; i < SubscribleTopic.subscrible.size(); ++i) {
                grab2Sub().subscribe(SubscribleTopic.subscrible.get(i), 1);
            }
            logger.info("startup emqtt success...");
            return true;
        }
        shutdown();
        return false;
    }

    public void shutdown() {
        subscribeArrayList.clear();
        publishArrayList.clear();
        logger.info("shutdown emqtt success...");
    }

    private boolean initPublish() {
        for (int i = 0; i < mqttConfigure.getCorePublishSize(); ++i) {
            Mqtt publish = new MqttPublish(threadPool, this,
                    new EmqttWrapper(mqttConfigure.getHost(),
                            "Publish_" + GetGUID(),
                            mqttConfigure.getUsername(),
                            mqttConfigure.getPassword(),
                            true,
                            true,
                            mqttConfigure.getKeepAliveInterval()));
            if (!publish.connect()) {
                logger.error("connect publish session failded, please check your broker.");
                return false;
            }
            publishArrayList.add(publish);
        }
        return true;
    }

    private boolean initSubscribe() {
        for (int i = 0; i < mqttConfigure.getCoreSubscribeSize(); ++i) {
            Mqtt subscrible = new MqttSubscrible(threadPool, this,
                    new EmqttWrapper(mqttConfigure.getHost(),
                            "Subscribe_" + GetGUID(),
                            mqttConfigure.getUsername(),
                            mqttConfigure.getPassword(),
                            true,
                            true,
                            mqttConfigure.getKeepAliveInterval()));
            if (!subscrible.connect()) {
                logger.error("connect publish session failded, please check your broker.");
                return false;
            }
            subscribeArrayList.add(subscrible);
        }
        return true;
    }
}
