package com.my.mqtt.producer;

import com.my.mqtt.producer.config.MqttConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableEurekaClient
//@Configuration
//@EnableConfigurationProperties
//@ComponentScan(basePackages = {"com.my.mqtt.producer" })
public class MyMqttProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMqttProducerApplication.class, args);
    }
}
