package com.schedule.my.schedule.schedule.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuartzTest {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
        System.out.println("定时任务执行开始时间 ： " + sdf.format(new Date()) + " ,时间戳：" + System.currentTimeMillis());
        System.out.println("main 当前线程名称: " + Thread.currentThread().getName());
        test1();
        //test2(System.currentTimeMillis());
    }

    public static void test1() {
        try {
            //任务名称
            String jobName = "command_id";
            //任务组
            String jobGroup = "group";

            //构建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(QuartsWorkJob.class)
                    .withDescription("description")
                    .usingJobData("name", jobName)    //向JobDataMap添加要传递的值
                    //.usingJobData("other", "value")
                    .build();

            //触发器名称
            String triName = "triName";
            //触发表达式
            String triCron = "*/10 * * * * ?";
            CronExpression expression = new CronExpression(triCron);
            //构建触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triName, jobGroup)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(expression))
                    .build();
            //创建调度器(Scheduler)
            SchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();

            //注册调度器
            scheduler.scheduleJob(jobDetail, trigger);

            //启动调度器
            scheduler.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过startAt(Date)指定启动时间以及withRepeatCount(int)重复次数达到在指定时间只执行一次的效果
     */
    public static void test2(Long time) {
        try {
            JobDetail detail = JobBuilder.newJob(QuartsWorkJob.class)
                    .withIdentity("data", "group0")
                    .usingJobData("data", "hello")
                    .usingJobData("name", "JobName")
                    .build();
            /**
             * startNow() 立即开始
             */
            time += 10*1000;
            Date date = new Date(time);
            System.out.println("时间戳：" + date.getTime());
            SimpleTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("data_trigger")
                    //.startNow()
                    .startAt(date)
                    .withSchedule(
                            /**
                             * withIntervalInSeconds(n) 每隔n秒执行
                             * repeatForever() 一直重复
                             * withRepeatCount(n) 重复n次
                             */
                            SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
                    .build();
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(detail, trigger);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
