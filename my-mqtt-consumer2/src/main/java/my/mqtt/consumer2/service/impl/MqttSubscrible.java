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
public class MqttSubscrible extends Mqtt {
    public MqttSubscrible(ThreadPool threadPool, MqttManager mqttManager, EmqttAsyncWrapper mqttWrapper) {
        super(threadPool, mqttManager,mqttWrapper);
    }

    @Override
    public boolean publish(String topic, byte[] payload, int qos, boolean retained){
        return false;
    }

    @Override
    public boolean subscribe(final String topicName, int qos){
        return mqttWrapper.subscribe(topicName, qos);
    }

    @Override
    public void unsubscribe(final String topicName){
        mqttWrapper.unsubscribe(topicName);
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
