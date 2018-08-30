package com.schedule.my.schedule.schedule.task;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.*;

public class ScheduledExecetorServiceTask {
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    static final long ONE_HOUR_TIME_INTERVAL = 60 * 60;

    private static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);

    /**
     * 延迟指定时间后执行某个指定任务
     */
    public static void delayExecution() {
        System.out.println("ScheduledExecetorService 开始" + sdf.format(new Date()));
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试ScheduledExecetorService方法："+sdf.format(new Date()));
            }
        }, 10, TimeUnit.SECONDS);
    }

    /**
     * 时间段(eg:早8点到晚8点间)延迟执行
     */
    public static void delayHourLoopExecution() {
        System.out.println("=============" + Thread.currentThread().getName());
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Calendar ca=Calendar.getInstance();
                //获取当时时间数
                int hour = ca.get(Calendar.HOUR_OF_DAY) ;
                if (hour != 15) {
                    System.out.println("未在时间段" + LocalDateTime.now().toLocalTime());
                    return;
                }
                System.out.println("时间段定时执行" + LocalDateTime.now().toLocalTime());
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}
