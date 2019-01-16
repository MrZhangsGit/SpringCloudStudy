package my.mqtt.consumer2.service.impl;

import my.mqtt.consumer2.service.MqttProcesser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * @module: com.berl.emqtt.service.impl
 * @author: chenfei
 * @date: 2018 2018/12/10
 * @version: v1.0
 */
public class MqttMsgHandler implements MqttProcesser {
    private Logger logger = LoggerFactory.getLogger(MqttMsgHandler.class);
    @Override
    public boolean process(String topic, ByteBuffer payload, int qos, boolean retain) {
        logger.info("handl: ...............");
        return true;
    }
}
