package com.oeasycloud.myredisserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MyRedisServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRedisServerApplication.class, args);
	}
}
