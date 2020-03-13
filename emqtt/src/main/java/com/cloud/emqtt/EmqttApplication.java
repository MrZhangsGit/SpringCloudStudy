package com.cloud.emqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableDiscoveryClient
@SpringBootApplication
public class EmqttApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmqttApplication.class, args);
    }
}

