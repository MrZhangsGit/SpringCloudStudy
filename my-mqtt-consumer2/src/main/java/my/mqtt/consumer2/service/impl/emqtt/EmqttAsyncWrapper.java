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
public class EmqttAsyncWrapper extends MqttWrapper {
    private MqttConnectOptions mqttConnectOptions;
    private MqttAsyncClient mqttAsyncClient;
    private Logger logger = LoggerFactory.getLogger(EmqttAsyncWrapper.class);

    public EmqttAsyncWrapper(String brokerUrl, String clientId, String userName, String password, boolean cleanSession, boolean quietMode, int keepAlive){
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
            mqttConnectOptions.setMaxInflight(100*10000);

            // Construct an MQTT blocking mode client
            mqttAsyncClient = new MqttAsyncClient(brokerUrl, clientid, new MemoryPersistence());

            // Set this wrapper as the callback handler
            mqttAsyncClient.setCallback(new EmqttAsyncCallback());

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public boolean connect() {
        try {
            if (!mqttAsyncClient.isConnected()){
                IMqttToken connectToken = mqttAsyncClient.connect(mqttConnectOptions);
                connectToken.waitForCompletion();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return mqttAsyncClient.isConnected();
    }

    @Override
    public void disconnect() {
        try {
            class onDisconnect implements IMqttActionListener{
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    logger.info("session id[" + asyncActionToken.getMessageId() +"]: disconnect broker[" + brokerUrl + "] success.");
                    mqtt.on_disconnect(true);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    logger.error("session id[" + asyncActionToken.getMessageId() +"]: disconnect broker[" + brokerUrl + "] failed, reason:" + exception);
                    mqtt.on_disconnect(false);
                }
            }
            IMqttToken disconnectToken = mqttAsyncClient.disconnect(null, new onDisconnect());
            System.out.println("session id[" + disconnectToken.getMessageId() + "]: disconnect broker[" + brokerUrl + "] ...");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean publish(String topicName, byte[] payload, int qos, boolean retained) {
        if (!mqttAsyncClient.isConnected()){
            return false;
        }
        try {
            class onPublish implements IMqttActionListener{
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    logger.info("session id[" + asyncActionToken.getMessageId() +"]: publish topic[" + topicName + "] payload ok." );
                    mqtt.on_publish(asyncActionToken.getMessageId());
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    logger.error("session id["+ asyncActionToken.getMessageId() +"]: publish topic[" + topicName + "] payload failed, reason:" + exception);
                }
            }
            IMqttToken publishToken = mqttAsyncClient.publish(topicName, payload, qos, retained,null, new onPublish());
            logger.info("session id["+ publishToken.getMessageId() + "]: publish topic[" + topicName + "] payload ...");
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean subscribe(String topicName, int qos) {
        if (!mqttAsyncClient.isConnected()){
            return false;
        }
        try {
            class onSubscribe implements IMqttActionListener{
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    logger.info("session id[" + asyncActionToken.getMessageId() +"]: subscribe topic[" + topicName + "] ok." );
                    mqtt.on_subscribe(asyncActionToken.getMessageId());
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    logger.error("session id["+ asyncActionToken.getMessageId() +"]: subscribe topic[" + topicName + "] failed, reason:" + exception);
                }
            }
            IMqttToken subscribeToken = mqttAsyncClient.subscribe(topicName, qos,null, new onSubscribe());
            logger.info("session id["+ subscribeToken.getMessageId() + "]: subscribe topic[" + topicName + "] ...");
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void unsubscribe(String topicName) {
        try {
            class onUnsubscribe implements IMqttActionListener {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    logger.info("session id[" + asyncActionToken.getMessageId() +"]: unsubscribe topic[" + topicName + "] ok." );
                    mqtt.on_unsubscribe(asyncActionToken.getMessageId());
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    logger.error("session id["+ asyncActionToken.getMessageId() +"]: unsubscribe topic[" + topicName + "] failed, reason:" + exception);
                }
            }
            IMqttToken unsubscribeToken = mqttAsyncClient.unsubscribe(topicName, null, new onUnsubscribe());
            logger.info("session id["+ unsubscribeToken.getMessageId() + "]: unsubscribe topic[" + topicName + "] ...");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void on_message(String topicName, byte[] payload, int qos, boolean retained) {
        mqtt.on_message(topicName, payload, qos, retained);
    }

    @Override
    public void on_disconnect() {
        mqtt.on_disconnect(true);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //
    public class EmqttAsyncCallback implements MqttCallback {
        @Override
        public void connectionLost(Throwable cause) {
            logger.error((new Date())+" - Connection Lost");
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
