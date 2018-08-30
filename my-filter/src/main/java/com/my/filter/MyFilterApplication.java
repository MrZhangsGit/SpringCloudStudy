package com.my.filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MyFilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFilterApplication.class, args);
	}
}
