package my.mqtt.consumer2.service;

/**
 * @module: com.berl.emqtt.service
 * @author: chenfei
 * @date: 2018 2018/12/10
 * @version: v1.0
 */
public abstract class MqttWrapper {
    protected Mqtt mqtt;

    // Private instance variables
    protected String  brokerUrl;
    protected String clientid;
    protected boolean quietMode;
    protected boolean clean;
    protected String password;
    protected String userName;
    protected int keepAlive;

    public MqttWrapper(String brokerUrl, String clientId, String userName, String password, boolean cleanSession, boolean quietMode, int keepAlive){
        this.brokerUrl = brokerUrl;
        this.clientid = clientId;
        this.password = password;
        this.userName = userName;
        this.quietMode = quietMode;
        this.clean 	   = cleanSession;
        this.keepAlive = keepAlive;
    }

    public void attachMqtt(Mqtt mqtt){
        this.mqtt = mqtt;
    }

    //connect MQ server
    public abstract boolean connect();
    public abstract void disconnect();

    public abstract boolean publish(String topic, byte[] payload, int qos, boolean retained);

    public abstract void on_disconnect();

    public abstract boolean subscribe(String s, int qos);
    public abstract void unsubscribe(String topic);

    // names in the functions commented to prevent unused parameter warning
    public abstract void on_message(String topicName, byte[] payload, int qos, boolean retained);



}
