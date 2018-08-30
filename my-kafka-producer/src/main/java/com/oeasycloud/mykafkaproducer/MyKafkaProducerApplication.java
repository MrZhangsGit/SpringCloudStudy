package com.oeasycloud.mykafkaproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableEurekaClient
public class MyKafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyKafkaProducerApplication.class, args);
	}
}
