package my.mqtt.consumer2.service.impl.emqtt;

import com.berl.emqtt.service.MqttWrapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @module: com.berl.emqtt.service.impl.emqtt
 * @author: chenfei
 * @date: 2018 2018/12/12
 * @version: v1.0
 */
public class EmqttWrapper extends MqttWrapper {
    private MqttConnectOptions mqttConnectOptions;
    private MqttClient mqttClient;
    private Logger logger = LoggerFactory.getLogger(EmqttWrapper.class);

    public EmqttWrapper(String brokerUrl, String clientId, String userName, String password, boolean cleanSession, boolean quietMode, int keepAlive){
        super(brokerUrl, clientId, userName, password, cleanSession, quietMode, keepAlive);
        try {
            // Construct the connection options object that contains connection parameters
            // such as cleanSession and LWT
            mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(cleanSession);
            if(password != null ) {
                mqttConnectOptions.setPassword(password.toCharArray());
            }
            if(userName != null) {
                mqttConnectOptions.setUserName(userName);
            }
            mqttConnectOptions.setKeepAliveInterval(keepAlive);
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setMaxInflight(65535);

            // Construct an MQTT blocking mode client
            mqttClient = new MqttClient(brokerUrl, clientid, new MemoryPersistence());

            // Set this wrapper as the callback handler
            mqttClient.setCallback(new EmqttCallback());

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    //connect MQ server
    public boolean connect() {
        try {
            if (!mqttClient.isConnected())
                mqttClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return mqttClient.isConnected();
    }
    @Override
    public void disconnect(){
        try {
            if (!mqttClient.isConnected())
                mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean publish(String topicName, byte[] payload, int qos, boolean retained) {
        try {
            if (mqttClient.isConnected()){
                String time = new Timestamp(System.currentTimeMillis()).toString();
                logger.info("Publishing at: " + time + " to topic \"" + topicName + "\" qos "+qos);
                // Create and configure a message
                MqttMessage message = new MqttMessage(payload);
                message.setQos(qos);
                message.setRetained(retained);

                // Send the message to the server, control is not returned until
                // it has been delivered to the server meeting the specified
                // quality of service.
                mqttClient.publish(topicName, message);
                return true;
            }
            logger.error("client lost connected server");
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void on_disconnect(){
        try {
            mqttClient.reconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean subscribe(String topicName, int qos) {
        try {
            if (mqttClient.isConnected()){
                String time = new Timestamp(System.currentTimeMillis()).toString();
                logger.info("Subscribing to topic \""+topicName+"\" qos "+qos);
                mqttClient.subscribe(topicName, qos);
                return true;
            }
            logger.error("client lost connected server");
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void unsubscribe(String topicName) {
        try {
            if (mqttClient.isConnected()){
                String time = new Timestamp(System.currentTimeMillis()).toString();
                logger.info("Unsubscribing to topic \""+topicName+"\"");
                mqttClient.unsubscribe(topicName);
            }
            logger.error("client lost connected server");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void on_message(String topicName, byte[] payload, int qos, boolean retained) {
        mqtt.on_message(topicName, payload, qos, retained);
    }

    public class EmqttCallback implements MqttCallback {
        @Override
        public void connectionLost(Throwable cause) {
            logger.info((new Date())+" - Connection Lost");
            on_disconnect();
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            // Called when a message arrives from the server that matches any
            // subscription made by the client
            String time = new Timestamp(System.currentTimeMillis()).toString();
            logger.info("message arrived:" +
                    "\n\tTime: " +time +
                    "\n\tTopic: " + topic +
                    "\n\tMessage: " + new String(message.getPayload()) +
                    "\n\tQoS: " + message.getQos() +
                    "\n\tRetained: " + message.isRetained());
            on_message(topic,message.getPayload(), message.getQos(), message.isRetained());
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    }
}
