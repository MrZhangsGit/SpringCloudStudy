package com.schedule.my.schedule.schedule.quartz;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Configuration
@EnableScheduling
@Component
public class QuartzScheduleTask implements SchedulingConfigurer {

    @Mapper
    public interface CronMapper {
        @Select("select cron from schedule_task limit 1")
        String getCron();
    }

    @Autowired
    @SuppressWarnings("all")
    CronMapper cronMapper;

    /**
     * 执行定时任务。读取数据库中存储的cron表达式执行定时任务
     * @param scheduledTaskRegistrar
     * 添加的是TriggerTask，目的循环读取数据库中设置好的执行周期
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        /*Date date = new Date();
        Date time = new Date("2018/7/6/ 9:49:06");
        System.out.println("进入Quartz定时任务" + date);
        //1.添加任务内容(Runnable)
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("Quartz执行定时任务: " + date);
            }
            //2.设置执行周期(Trigger)
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                *//**
                 * 注:此处实为Spring Task，不支持年份，所以cron只有6个域
                 *//*
                //2.1 从数据库获取执行周期
                String cron = cronMapper.getCron();
                *//*String cron = "06 50 09 06 07 ?";*//*
                *//*String cron = TransformationUtils.formatDateByPattern(time, "ss mm HH dd MM ? yyyy");*//*
                System.out.println("====Cron:" + cron);
                //2.2 合法性校验.
                if (StringUtils.isEmpty(cron)) {
                    // Omitted Code ..
                }
                //2.3 返回执行周期(Date)
                return new CronTrigger(cron).nextExecutionTime(triggerContext);
            }
        });*/
    }
}
