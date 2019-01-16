package my.mqtt.consumer2.service;
/**
 * @module: com.berl.emqtt.service
 * @author: chenfei
 * @date: 2018 2018/12/10
 * @version: v1.0
 */
public abstract class Mqtt {
    protected MqttManager mqttManager;
    protected ThreadPool threadPool;

    protected MqttWrapper mqttWrapper;

    public Mqtt(ThreadPool threadPool, MqttManager mqttManager, MqttWrapper mqttWrapper) {
        this.threadPool = threadPool;
        this.mqttManager = mqttManager;
        this.mqttWrapper = mqttWrapper;
        mqttWrapper.attachMqtt(this);
    }

    //connect MQ server
    public Boolean connect() { return mqttWrapper.connect(); }
    public void disconnect(){ mqttWrapper.disconnect(); }

    //publish
    public boolean publish(String topic, byte[] payload, int qos, boolean retained) { return false; }
    //subscribe
    public boolean subscribe(final String topicName, int qos) { return false; }
    //unsubscribe
    public void unsubscribe(final String topicName) {}

    // names in the functions commented to prevent unused parameter warning
    public void on_connect(boolean bStatus){}
    public void on_disconnect(boolean bStatus){}
    public void on_publish(int mid){}
    public void on_message(final String topic, byte[] payload, int qos, boolean retain){}
    public void on_subscribe(int mid){}
    public void on_unsubscribe(int mid){}
}
