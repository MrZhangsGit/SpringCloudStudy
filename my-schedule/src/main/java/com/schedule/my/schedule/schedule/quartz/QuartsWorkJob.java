package com.schedule.my.schedule.schedule.quartz;

import com.alibaba.fastjson.JSONObject;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuartsWorkJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");

        //可在JobDataMap中传递值,如一些业务值
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println("QuartsWorkJob 当前线程名称: " + Thread.currentThread().getName());
        System.out.println("定时任务..." + dataMap.getString("name") + "...执行时间 ： " + sdf.format(new Date()));
        System.out.println(JSONObject.toJSONString(dataMap));
    }
}
