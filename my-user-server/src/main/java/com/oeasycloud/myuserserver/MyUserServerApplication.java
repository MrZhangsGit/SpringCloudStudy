package com.oeasycloud.myuserserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MyUserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyUserServerApplication.class, args);
	}
}
