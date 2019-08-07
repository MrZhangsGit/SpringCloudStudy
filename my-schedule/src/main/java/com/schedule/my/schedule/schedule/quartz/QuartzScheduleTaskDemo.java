package com.schedule.my.schedule.schedule.quartz;

import com.alibaba.fastjson.JSON;
import com.schedule.my.schedule.schedule.utils.TransformationUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class QuartzScheduleTaskDemo implements SchedulingConfigurer {

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
     *
     * 这个解析cron的地方只支持六位！“Quartz”根本就不是“Quartz” （项目名字就是  Company_Quartz），实际是Spring Task。
     * Spring Task是Quartz的弱版，quartz支持年份，而Spring Task不支持。
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        Date date = new Date();
        Date time = new Date("2019/1/23 11:26:05");
        System.out.println("进入Quartz定时任务" + date);
        //1.添加任务内容(Runnable)
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("Quartz执行定时任务: " + date);
            }
            //设置执行周期(Trigger)
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                /**
                 * 注:此处实为Spring Task，不支持年份，所以cron只有6个域
                  */
                //从数据库获取执行周期
                //String cron = cronMapper.getCron();
                String cron = "10 31 16 24 01 ?";
                //String cron = TransformationUtils.formatDateByPattern(time);
                System.out.println("====Cron:" + cron);
                //合法性校验.
                if (StringUtils.isEmpty(cron)) {
                    System.out.println("===Cron不可为空!");
                }
                int oCronLength = cron.length();
                cron.replaceAll(" ", "");
                if ((oCronLength - cron.length()) > 5) {
                    System.out.println("===Cron不支持(超过6位)!");
                }

                //返回执行周期(Date)
                return new CronTrigger(cron).nextExecutionTime(triggerContext);
            }
        });
    }

    public void task(String cronTask) {
        log.info("{}---Start---入参:{}", Thread.currentThread().getStackTrace()[1].getMethodName(), JSON.toJSONString(cronTask));
//        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, new Trigger() {
//            @Override
//            public Date nextExecutionTime(TriggerContext triggerContext) {
//                System.out.println("---:" + cronTask);
//                try {
//                    return new CronTrigger(cronTask).nextExecutionTime(triggerContext);
//                } catch (Exception e) {
//                    log.error(JSON.toJSONString(e));
//                }
//                return new Date();
//            }
//        });
    }

    public void schedule(String cronTask){
        log.info("---{}---入参:{}", Thread.currentThread().getStackTrace()[1].getMethodName(), JSON.toJSONString(cronTask));

    }
}
