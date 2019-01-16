package com.my.aspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MyAspectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAspectApplication.class, args);
    }

}

