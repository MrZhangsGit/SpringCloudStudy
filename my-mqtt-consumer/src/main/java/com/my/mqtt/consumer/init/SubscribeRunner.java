package com.my.mqtt.consumer.init;

import com.my.mqtt.consumer.config.mqttWapper.SubscribeConn;
import com.my.mqtt.consumer.service.IMqttSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * <desc>
 *      启动MQTT订阅
 * </desc>
 * @author zhangs
 * @createDate 2018/08/30
 */
@Slf4j
@Component
@Order(value = 2)
public class SubscribeRunner implements ApplicationRunner {
    @Autowired
    private IMqttSubscribe iMqttSubscribe;

    private MqttClient mqttSubClient;

    private ScheduledExecutorService scheduler;

    @Autowired
    private SubscribeConn subscribeConn;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info(" MQTT Subscribe Server 开始...");
        subscribeConn.getConn();
        /*clientListener();*/
    }

    /**
     * 注意：此处利用线程监控mqttClient的方案有问题
     * 1.线程启动时，client未必创建好了
     * 2.此处引用mqtt的初始条件是需要将mqtt与使用处隔离开，方便扩展(比如换其他协议)。
     *     但此处利用线程监控的client是mqtt的，违背初衷，当然也可以在mqttClient外再封装一层进行监控
     */
    private void clientListener() {
        log.info("---启动线程监控client状态---");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.info("{}，---正在监控client状态---", Thread.currentThread().getName());
                if (!mqttSubClient.isConnected()) {
                    log.info("---client状态:连接断开---");
                    try {
                        mqttSubClient = subscribeConn.getConn();
                        log.info("---client状态:重新连接---");
                    } catch (Exception e) {
                        log.error("---client重连异常---:{}", e);
                    }
                }
                log.info("---client状态:连接正常---");
            }
        }, 0, 3 * 1000, TimeUnit.MILLISECONDS);
    }
}
