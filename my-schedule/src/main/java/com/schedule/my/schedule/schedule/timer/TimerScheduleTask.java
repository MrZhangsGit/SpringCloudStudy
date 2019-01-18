package com.schedule.my.schedule.schedule.timer;

import com.schedule.my.schedule.po.TaskMO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Timer是jdk中提供的一个定时器工具，使用的时候会在主线程之外起一个单独的线程执行指定的计划任务，可以指定执行一次或者反复执行多次。
 * TimerTask是一个实现了Runnable接口的抽象类，代表一个可以被Timer执行的任务。
 *
 * schedule 和 scheduleAtFixedRate都是任务调度方法，
 * schedule会保证任务的间隔是按照定义的period参数严格执行的，如果某一次调度时间比较长，那么后面的时间会顺延，保证调度间隔都是period；
 * scheduleAtFixedRate是严格按照调度时间来的，如果某次调度时间太长了，那么会通过缩短间隔的方式保证下一次调度在预定时间执行。
 * eg:每个3秒调度一次，那么正常就是0,3,6,9s这样的时间，
 *    如果第二次调度花了2s的时间，如果是schedule，就会变成0,3+2,8,11这样的时间，保证间隔，
 *    而scheduleAtFixedRate就会变成0,3+2,6,9，压缩间隔，保证调度时间。
 *
 * 每一个Timer仅对应唯一一个线程。
 * Timer不保证任务执行的十分精确。
 * Timer类的线程安全的。
 *
 * 可是看到Timer还提供了purge这个方法，注释是“从task queue里移除所有标记为canceled的task”。
 * 因TimerTask也有cancel方法，对比Timer的cancel方法，TimerTask的cancel一次只取消一个Task。purge方法就是用来释放内存引用的。
 * purge方法会检查timer队列里标记为canceled的task，将对它的引用置为null，否则TimerTask的引用没被释放会有内存泄露。
 *
 * Timer执行程序是有可能延迟1、2毫秒，如果是1秒执行一次的任务，1分钟有可能延迟60毫秒，一小时延迟3600毫秒。若执行定时任务在毫秒级别的误差不容忽视。
 *
 * Timer只创建了一个线程。当你的任务执行的时间超过设置的延时时间将会产生一些问题。
 * Timer创建的线程没有处理异常，因此一旦抛出非受检异常，该线程会立即终止。
 * JDK 5.0以后推荐使用ScheduledThreadPoolExecutor。该类属于Executor Framework，它除了能处理异常外，还可以创建多个线程。
 */
@Component
@Slf4j
public class TimerScheduleTask {
    Timer timer;

    /**
     * 起始时间执行(循环)
     * schedule(TimerTask task, Date firstTime, long period)
     * @throws Exception
     */
    public void startTimeExecute(TaskMO task) {
        try {
            Calendar startDate = Calendar.getInstance();

            //设置开始执行的时间:年-月-日-时-分-秒 00:00:00
            int year = startDate.get(Calendar.YEAR);
            int month = startDate.get(Calendar.MONTH);
            int day = startDate.get(Calendar.DATE);
            int hour = task.getStartHour();
            int minute = task.getStartMinute();
            int second = task.getStartSecond();
            startDate.set(year, month, day, hour, minute, second);

            //定时器实例
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 定时器主要执行的代码块
                    log.info("定时器主要执行的代码!---(设置起始时间、执行间隔时间)---{}", LocalDateTime.now().toLocalTime());
                }
            }, startDate.getTime(), task.getPeriod() * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 延时执行(循环)
     * schedule(TimerTask task, long delay, long period)
     * @param task
     */
    public void delayedExecute(TaskMO task) {
        try {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 定时器主要执行的代码块
                    log.info("定时器主要执行的代码!---设置延时执行时间、执行间隔时间---{}", LocalDateTime.now().toLocalTime());
                }
            }, task.getInitialDelay()*1000, task.getPeriod()*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 执行一次
     * (TimerTask task, Date time)
     * @param task
     */
    public void onceExecute(TaskMO task) {
        try {
            Calendar startDate = Calendar.getInstance();

            //设置开始执行的时间:年-月-日-时-分-秒 00:00:00
            int year = startDate.get(Calendar.YEAR);
            int month = startDate.get(Calendar.MONTH);
            int day = startDate.get(Calendar.DATE);
            int hour = task.getStartHour();
            int minute = task.getStartMinute();
            int second = task.getStartSecond();
            startDate.set(year, month, day, hour, minute, second);

            //定时器实例
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 定时器主要执行的代码块
                    log.info("定时器主要执行的代码!---执行一次---{}", LocalDateTime.now().toLocalTime());
                }
            }, startDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止
     * 有多个但无法指定停止哪个(测试看到的是停止最近开启的一个)
     */
    public void stopExecute() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
