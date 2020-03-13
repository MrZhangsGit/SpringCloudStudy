package com.cloud.emqtt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @module: com.cloud.emqtt.config
 * @author: chenfei
 * @date: 2018 2018/12/10
 * @version: v1.0
 */
@Slf4j
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfigure {
    private String host;
    private String username;
    private String password;
    private int connectionTimeout;
    private int keepAliveInterval;
    private int corePublishSize;
    private int maxPublishSize;
    private int coreSubscribeSize;
    private int maxSubscribeSize;

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

    public int getCorePublishSize() {
        return corePublishSize;
    }

    public void setCorePublishSize(int corePublishSize) {
        this.corePublishSize = corePublishSize;
    }

    public int getMaxPublishSize() {
        return maxPublishSize;
    }

    public void setMaxPublishSize(int maxPublishSize) {
        this.maxPublishSize = maxPublishSize;
    }

    public int getCoreSubscribeSize() {
        return coreSubscribeSize;
    }

    public void setCoreSubscribeSize(int coreSubscribeSize) {
        this.coreSubscribeSize = coreSubscribeSize;
    }

    public int getMaxSubscribeSize() {
        return maxSubscribeSize;
    }

    public void setMaxSubscribeSize(int maxSubscribeSize) {
        this.maxSubscribeSize = maxSubscribeSize;
    }

    @Override
    public String toString() {
        return "MqttConfigure{" +
                "host='" + host + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", connectionTimeout=" + connectionTimeout +
                ", keepAliveInterval=" + keepAliveInterval +
                ", corePublishSize=" + corePublishSize +
                ", maxPublishSize= " + maxPublishSize +
                ", coreSubscribeSize= " + coreSubscribeSize +
                ", maxSubscribeSize= " + maxSubscribeSize +
                '}';
    }
}
