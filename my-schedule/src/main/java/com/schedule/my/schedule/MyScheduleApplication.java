package com.schedule.my.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class MyScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyScheduleApplication.class, args);
//        ScheduledExecetorServiceTask.delayHourLoopExecution();
//        TimerScheduleTask.timePointExecute();
    }
}
