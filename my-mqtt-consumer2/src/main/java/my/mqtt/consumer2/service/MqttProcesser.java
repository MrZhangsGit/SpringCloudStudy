package my.mqtt.consumer2.service;

import java.nio.ByteBuffer;

/**
 * @module: com.berl.emqtt.service
 * @author: chenfei
 * @date: 2018 2018/12/10
 * @version: v1.0
 */
public interface MqttProcesser {
    boolean process(String topic, ByteBuffer payload, int qos, boolean retain);
}
