package my.mqtt.consumer2.service.impl;

import my.mqtt.consumer2.service.Mqtt;
import my.mqtt.consumer2.service.MqttManager;
import my.mqtt.consumer2.service.MqttWrapper;
import my.mqtt.consumer2.service.ThreadPool;
import my.mqtt.consumer2.service.impl.emqtt.EmqttAsyncWrapper;

/**
 * @module: com.berl.emqtt.service.impl
 * @author: chenfei
 * @date: 2018 2018/12/10
 * @version: v1.0
 */
public class MqttPublish extends Mqtt {
    public MqttPublish(ThreadPool threadPool, MqttManager mqttManager, EmqttAsyncWrapper mqttWrapper) {
        super(threadPool, mqttManager, mqttWrapper);
    }

    @Override
    public boolean publish(String topic, byte[] payload, int qos, boolean retained){
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
    public void on_publish(int mid){
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
