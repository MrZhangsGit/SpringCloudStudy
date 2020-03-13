package com.oeasycloud.myeurekaclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: zhangs
 * @Date: 2018/11/16 15:29
 * @Description: 在spring容器启动后创建线程池
 */
@Slf4j
@Component
@Order(value = 1)
public class ThreadPoolRunner implements ApplicationRunner {
    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {
        log.info("创建线程池");
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(300);
        //	queue = new LinkedBlockingQueue<>(100);//无界队列
        //	queue = new ArrayBlockingQueue<Runnable>(100);	//有界队列
        //	queue = new SynchronousQueue<Runnable>();	//默认
        /**
         * 饱和策略
         * 当队列满时，此时便是饱和策略发挥作用的时候了，JDK中定义了四种饱和策略：
         * 1、AbortPolicy：终止策略是默认的饱和策略，当队列满时，会抛出一个RejectExecutionException异常（第一段代码就是例子），客户可以捕获这个异常，根据需求编写自己的处理代码
         * 2、DiscardPolicy：策略会悄悄抛弃该任务。
         * 3、DiscardOldestPolicy：策略将会抛弃下一个将要执行的任务，如果此策略配合优先队列PriorityBlockingQueue，该策略将会抛弃优先级最高的任务
         * 4、CallerRunsPolicy：调用者运行策略，该策略不会抛出异常，不会抛弃任务，而是将任务回退给调用者线程执行（调用execute方法的线程），由于任务需要执行一段时间，所以在此期间不能提交任务，从而使工作线程有时间执行正在执行的任务。
         */
        threadPoolExecutor = new ThreadPoolExecutor(20, 200, 10, TimeUnit.SECONDS, queue ,new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
