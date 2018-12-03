package com.my.mqtt.consumer.init;

import com.my.mqtt.consumer.service.IMqttSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <desc>
 *      启动MQTT订阅
 * </desc>
 * @author zhangs
 * @createDate 2018/08/30
 */
@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    private IMqttSubscribe iMqttSubscribe;

    String TOPIC = "MQTT_PRODUCER_TOPIC";

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info(" MQTT Subscribe Server 开始...");
        iMqttSubscribe.subscribe(TOPIC);
    }
}
