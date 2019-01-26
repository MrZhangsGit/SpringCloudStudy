package com.schedule.my.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.schedule.my.schedule")
public class MyScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyScheduleApplication.class, args);
    }
}
