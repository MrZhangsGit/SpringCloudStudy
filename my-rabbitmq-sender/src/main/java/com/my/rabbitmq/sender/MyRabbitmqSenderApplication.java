package com.my.rabbitmq.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MyRabbitmqSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRabbitmqSenderApplication.class, args);
	}

}
