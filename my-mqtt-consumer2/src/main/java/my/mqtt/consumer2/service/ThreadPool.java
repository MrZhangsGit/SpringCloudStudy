package my.mqtt.consumer2.service;

import my.mqtt.consumer2.config.ThreadsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @module: com.berl.emqtt.service
 * @author: chenfei
 * @date: 2018 2018/12/11
 * @version: v1.0
 */
@Component // 关键1
@EnableAsync
@EnableConfigurationProperties(ThreadsConfig.class)
public class ThreadPool {
    @Autowired
    private ThreadsConfig threadsConfig;

    private ThreadPoolTaskExecutor threadPoolExecutor;

    // 关键3
    @PostConstruct
    public void init() {
        // 设置核心线程数
        threadPoolExecutor.setCorePoolSize(threadsConfig.getCorePoolSize());
        // 设置最大线程数
        threadPoolExecutor.setMaxPoolSize(threadsConfig.getMaxPoolSize());
        // 设置队列容量
        threadPoolExecutor.setQueueCapacity(threadsConfig.getQueueCapacity());
        // 设置线程活跃时间（秒）
        threadPoolExecutor.setKeepAliveSeconds(threadsConfig.getKeepAliveSeconds());
        // 设置默认线程名称
        threadPoolExecutor.setThreadNamePrefix("work-thread");
        // 设置拒绝策略
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        threadPoolExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolExecutor.initialize();
    }

    public ThreadPool() {
        threadPoolExecutor = new ThreadPoolTaskExecutor();
    }

    public void commint(Runnable task) {
        try {
            threadPoolExecutor.execute(task);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}