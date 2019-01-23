package com.schedule.my.schedule.schedule.task;

import com.schedule.my.schedule.po.TaskMO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.*;

/**
 * ScheduledExecutorService是基于线程池设计的定时任务类,每个调度任务都会分配到线程池中的一个线程去执行,也就是说,任务是并发执行,互不影响。
 * 需要注意,只有当调度任务来的时候,ScheduledExecutorService才会真正启动一个线程,其余时间ScheduledExecutorService都是出于轮询任务的状态。
 *
 * 支持周期性执行以及延时执行
 *
 * ScheduledExecutorService 中两种最常用的调度方法 ScheduleAtFixedRate 和 ScheduleWithFixedDelay
 * ScheduleAtFixedRate 每次执行时间为上一次任务开始起向后推一个时间间隔，
 *     即每次执行时间为 :initialDelay, initialDelay+period, initialDelay+2*period, …；
 * ScheduleWithFixedDelay 每次执行时间为上一次任务结束起向后推一个时间间隔，
 *     即每次执行时间为：initialDelay, initialDelay+executeTime+delay, initialDelay+2*executeTime+2*delay。
 * 由此可见，ScheduleAtFixedRate 是基于固定时间间隔进行任务调度，ScheduleWithFixedDelay 取决于每次任务执行的时间长短，
 *     是基于不固定时间间隔进行任务调度。
 *
 * ScheduledExecutorService继承的ExecutorService中有isShutdown()、shutdown()、shutdownNow()用于停止相关。
 */
@Component
@Slf4j
public class ScheduledExecutorServiceTask {
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);

    /**
     * 延迟指定时间后执行某个指定任务
     * 单次执行
     */
    public void delayExecute(TaskMO task) {
        log.info("ScheduledExecutorService Start...{}...{}", Thread.currentThread().getStackTrace()[0].getMethodName(),
                sdf.format(new Date()));
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("{}...延迟指定时间后执行指定任务...", sdf.format(new Date()));
            }
        }, task.getInitialDelay(), TimeUnit.SECONDS);
    }

    /**
     * 时间段(eg:早8点到晚8点间)延迟执行
     * 循环执行。定时任务开始后每隔指定间隔时间都会执行
     * initialDelay:定时任务开始时的延迟时间
     * period:定时任务开始后的间隔时间
     * unit:时间单位
     */
    public void periodExecute(TaskMO task) {
        log.info("ScheduledExecetorService Start...{}...{}", Thread.currentThread().getName(), sdf.format(new Date()));
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Calendar ca = Calendar.getInstance();
                //获取当时时间数
                int minute = ca.get(Calendar.MINUTE) ;
                int second = ca.get(Calendar.SECOND);
                /*if (minute <= task.getEndMinute() && minute >= task.getStartMinute()) {*/
                    if (second <= task.getEndSecond() && second >= task.getStartSecond()) {
                        log.info("{}...{}...时间段内执行任务", LocalDateTime.now().toLocalTime(),
                                Thread.currentThread().getStackTrace()[0].getMethodName());
                    }
                /*}*/ else {
                    log.info("{}...{}...未在时间段内执行任务", LocalDateTime.now().toLocalTime(),
                            Thread.currentThread().getStackTrace()[0].getMethodName());
                }
            }
        }, task.getInitialDelay(), task.getPeriod(), TimeUnit.SECONDS);
    }

    public void stopExecute() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }
}
