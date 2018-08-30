package com.oeasycloud.mykafkaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MyKafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyKafkaConsumerApplication.class, args);
	}
}
