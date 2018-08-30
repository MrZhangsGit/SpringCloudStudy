package com.schedule.my.schedule.schedule.timer;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimerScheduleTask {
    /**
     * 1小时的毫秒设定
     */
    static final int ONE_HOUR_TIME_INTERVAL = 60 * 60 * 1000;

    /**
     * 时间点执行
     * @throws Exception
     */
    public static void timePointExecute() {
        try {
            Calendar startDate = Calendar.getInstance();

            //设置开始执行的时间:年-月-日-时-分-秒 00:00:00
            int year = startDate.get(Calendar.YEAR);
            int month = startDate.get(Calendar.MONTH);
            int day = startDate.get(Calendar.DATE);
            int hour = 16;
            int minute = 50;
            int second = 0;
            startDate.set(year, month, day, hour, minute, second);

            //定时器实例
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 定时器主要执行的代码块
                    System.out.println("定时器主要执行的代码!" + LocalDateTime.now().toLocalTime());
                }
            }, startDate.getTime(), ONE_HOUR_TIME_INTERVAL);

        /*ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //do something
            }
        },ONE_HOUR_TIME_INTERVAL,3, TimeUnit.MILLISECONDS);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
