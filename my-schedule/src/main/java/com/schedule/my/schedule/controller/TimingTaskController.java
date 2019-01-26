package com.schedule.my.schedule.controller;

import com.alibaba.fastjson.JSON;
import com.schedule.my.schedule.po.TaskMO;
import com.schedule.my.schedule.schedule.quartz.QuartzScheduleTaskDemo;
import com.schedule.my.schedule.schedule.task.ScheduledExecutorServiceTask;
import com.schedule.my.schedule.schedule.timer.TimerScheduleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/17
 */
@RestController
@Slf4j
public class TimingTaskController {

    @Autowired
    private TimerScheduleTask timerScheduleTask;

    @Autowired
    private ScheduledExecutorServiceTask scheduledExecutorServiceTask;

    @Autowired
    private QuartzScheduleTaskDemo quartzScheduleTaskDemo;

    @RequestMapping(value = "/timer/start")
    public void timerStart(@RequestBody TaskMO task) {
        timerScheduleTask.startTimeExecute(task);
    }

    @RequestMapping(value = "/timer/delayed")
    public void delayed(@RequestBody TaskMO task) {
        Integer frequency = task.getFrequency();
        if (0 == frequency) {
            timerScheduleTask.delayedExecute(task);
        } else {
            timerScheduleTask.onceExecute(task);
        }
    }

    @RequestMapping(value = "/timer/stop")
    public void timerStop() {
        timerScheduleTask.stopExecute();
    }

    @RequestMapping(value = "/schedule/delay")
    public void scheduleDelay(@RequestBody TaskMO task) {
        log.info("{}...延迟指定时间后执行指定任务", LocalDateTime.now().toLocalTime());
        scheduledExecutorServiceTask.delayExecute(task);
    }

    @RequestMapping(value = "/schedule/period")
    public void schedulePeriod(@RequestBody TaskMO task) {
        log.info("{}...时间段内执行指定任务", LocalDateTime.now().toLocalTime());
        scheduledExecutorServiceTask.periodExecute(task);
    }

    @RequestMapping(value = "/testTask", method = RequestMethod.GET)
    public void testTask(@RequestParam String cronTask) {
        log.info("{}---Start---入参:{}", Thread.currentThread().getStackTrace()[1].getMethodName(), JSON.toJSONString(cronTask));
        quartzScheduleTaskDemo.task(cronTask);
    }
}
