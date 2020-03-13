package com.my.mqtt.consumer.config.mqttWapper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.UUID;

/**
 * @Auther: zhangs
 * @Date: 2018/4/19 15:18
 * @Description: 消息发布者配置类
 */
@Data
@Slf4j
@Component
@EnableConfigurationProperties({MqttConfiguration.class})
public class PublishConn {
    @Autowired
    private MqttConfiguration mqttConfiguration;
    private MqttConnectOptions mqttConnectOptions;
    private MqttClient mqttClient;

    /**
     * 连接emq服务器
     */
    public MqttClient getConn(){
        if (mqttClient == null) {
            try {
                String clientId = UUID.randomUUID().toString() +
                        "[" + InetAddress.getLocalHost().getHostAddress() + "-" + mqttConfiguration.getPublishClientId() + "]";
                // host为主机名，clientId即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientId的保存形式，默认为以内存保存
                mqttClient = new MqttClient(mqttConfiguration.getHost(), clientId, new MemoryPersistence());
                // MQTT的连接设置
                mqttConnectOptions = new MqttConnectOptions();
                // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
                mqttConnectOptions.setCleanSession(true);
                // 设置连接的用户名
                mqttConnectOptions.setUserName(mqttConfiguration.getUsername());
                // 设置连接的密码
                mqttConnectOptions.setPassword(mqttConfiguration.getPassword().toCharArray());
                // 设置超时时间 单位为秒
                mqttConnectOptions.setConnectionTimeout(mqttConfiguration.getConnectionTimeout());
                mqttConnectOptions.setAutomaticReconnect(true);
                // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
                mqttConnectOptions.setKeepAliveInterval(mqttConfiguration.getKeepAliveInterval());
                //配置多个服务器列表，一个挂掉会自动切换到其他正常的服务器，挂掉的服务器正常后，客户端会再次切换回来
                if(mqttConfiguration.getHosts() !=null && mqttConfiguration.getHosts().length>0){
                    mqttConnectOptions.setServerURIs(mqttConfiguration.getHosts());
                }
                mqttClient.connect(mqttConnectOptions);
                log.info("连接服务器成功");
            } catch (Exception e) {
                e.printStackTrace();
                log.info("连接服务器失败");
            }
        }
        return mqttClient;
    }
}
