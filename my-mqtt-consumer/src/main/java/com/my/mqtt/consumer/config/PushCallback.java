package com.my.mqtt.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangs
 * @createDate 2018/5/21
 *
 * 发布消息的回调类
 *
 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。
 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。
 * 在回调中，将它用来标识已经启动了该回调的哪个实例。
 * 必须在回调类中实现三个方法：
 *
 *  public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。
 *
 *  public void connectionLost(Throwable cause)在断开连接时调用。
 *
 *  public void deliveryComplete(MqttDeliveryToken token))
 *  接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。
 *  由 MqttClient.connect 激活此回调。
 *
 */
@Slf4j
@Component
public class PushCallback implements MqttCallback {
    /**
     * 消息订阅者配置类
     */
    @Autowired
    private SubscribeConn subscribeConn;

    @Override
    public void connectionLost(Throwable cause) {
        log.info("连接断开，进行重连");
        subscribeConn.getConn();
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("接收消息主题:{},接收消息Qos:{},接收消息内容:{}",topic,mqttMessage.getQos(),new String(mqttMessage.getPayload()));
        String content = new String(mqttMessage.getPayload());
        log.info("消费者接收消息:" + content);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("deliveryComplete:{}",iMqttDeliveryToken.isComplete());
    }
}
