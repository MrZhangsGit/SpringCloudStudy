package com.my.mqtt.consumer.config.mqttWapper;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.my.mqtt.consumer.config.mqttWapper.SubscribeConn;
import com.my.mqtt.consumer.init.PublishRunner;
import com.my.mqtt.consumer.service.IMqttSubscribe;
import com.my.mqtt.consumer.utils.queue.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
public class PushCallback implements MqttCallbackExtended {
    /**
     * 消息订阅者配置类
     */
    @Autowired
    private PublishConn publishConn;

    @Autowired
    private QueueHandleUtil queueHandleUtil;

    @Autowired
    private IMqttSubscribe iMqttSubscribe;

    private ScheduledExecutorService scheduler;

    String TOPIC = "MQTT_PRODUCER_TOPIC";

    @Override
    public void connectionLost(Throwable cause) {
        log.info("连接断开，进行重连...");
        /*subscribeConn.getConn();*/
        /*MqttClient mqttSubClient = subscribeConn.getConn();
        clientListener(mqttSubClient);*/
    }

    private void clientListener(MqttClient mqttSubClient) {
        while (true) {
            if (!mqttSubClient.isConnected()) {
                log.info("进行重连...");
                try {
                    log.info("PushCallback---clientListener---mqttClient内存地址::{}", System.identityHashCode(mqttSubClient));
                    Thread.sleep(3000);
                    mqttSubClient.reconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            if (mqttSubClient.isConnected()) {
                log.info("连接断开，重连成功...");
                break;
            }
        }
        iMqttSubscribe.subscribe(TOPIC);
    }

    private void clientListenerSync(MqttClient mqttSubClient) {
        log.info("---启动线程监控client状态并重连---");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!mqttSubClient.isConnected()) {
                    log.info("进行重连...");
                    try {
                        log.info("PushCallback---clientListenerSync---mqttClient内存地址::{}", System.identityHashCode(mqttSubClient));
                        mqttSubClient.reconnect();
                    } catch (Exception e) {
                        log.error("---client重连异常---:{}", e);
                        e.printStackTrace();
                    }
                }
                log.info("---client状态:连接正常---");
                iMqttSubscribe.subscribe(TOPIC);
            }
        }, 0, 3 * 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("接收消息主题:{},接收消息Qos:{},接收消息内容:{}",topic,mqttMessage.getQos(),new String(mqttMessage.getPayload()));
        String content = new String(mqttMessage.getPayload());
        log.info("MQ消费者接收消息:" + content);
        /*Thread.sleep(10000);*/
        queueHandleUtil.advanceDisruptor(content);
        /*queueHandleUtil.advanceDisruptor2(content);*/
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("deliveryComplete:{}",iMqttDeliveryToken.isComplete());
    }

    /**
     * Called when the connection to the server is completed successfully.
     *
     * @param reconnect If true, the connection was the result of automatic reconnect.
     * @param serverURI The server URI that the connection was made to.
     */
    /**
     * 开启SubscribeConn中的自动重连mqttConnectOptions.setAutomaticReconnect(true);getConn()中避免多次创建client
     * connectComplete中的reconnect表示重连是否成功，项目启动时为false，断开后重连成功则为true。
     *     即只要client连接成功都会走connectComplete()方法，则此处适合初始化订阅。
     * 当断开后由于自动重连，连接成功后走connectComplete()，其中订阅用到的client也是同一个
     */
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("connectComplete---连接成功...{}", reconnect);
        iMqttSubscribe.subscribe(TOPIC);
        publishConn.getConn();
    }
}
