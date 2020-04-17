package com.oeasycloud.myeurekaclient.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/4/17
 */
@Component
public class AsyncTask {
    public static Random random = new Random();

    @Async
    public Future<String> doTaskOne() throws Exception{
        System.out.println("开始任务一   " + Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(2000));
        System.out.println("完成任务一  耗时:" + (System.currentTimeMillis() - start) + "ms");
        return new AsyncResult<>("任务一完成");
    }

    @Async("taskExecutor")
    public Future<String> doTaskTwo() throws Exception{
        System.out.println("开始任务二   " + Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(2000));
        System.out.println("完成任务二  耗时:" + (System.currentTimeMillis() - start) + "ms");
        return new AsyncResult<>("任务二完成");
    }

    @Async
    public void doTaskThree() throws Exception{
        System.out.println("开始任务三   " + Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(2000));
        System.out.println("完成任务三  耗时:" + (System.currentTimeMillis() - start) + "ms");
    }
}
