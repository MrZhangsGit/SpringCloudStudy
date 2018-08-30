package com.oeasycloud.mymybatisserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MyMybatisServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyMybatisServerApplication.class, args);
	}
}
