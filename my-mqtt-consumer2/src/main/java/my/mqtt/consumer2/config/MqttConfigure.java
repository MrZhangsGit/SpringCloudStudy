package my.mqtt.consumer2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @module: com.berl.emqtt.config
 * @author: chenfei
 * @date: 2018 2018/12/10
 * @version: v1.0
 */
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfigure {
    private String host;
    private String username;
    private String password;
    private int connectionTimeout;
    private int keepAliveInterval;
    private Map<String, Object> session = new HashMap<>();

    public int getSession(String keyName) {
        return (int) session.get(keyName);
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public void setKeepAliveInterval(int keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    @Override
    public String toString() {
        return "MqttConfigure{" +
                "host='" + host + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", connectionTimeout=" + connectionTimeout +
                ", keepAliveInterval=" + keepAliveInterval +
                ", session=" + session +
                '}';
    }
}
