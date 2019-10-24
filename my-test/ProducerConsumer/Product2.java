package ProducerConsumer;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangs
 * @Description 利用BlockingQueue阻塞队列方式实现生产者-消费者
 * @createDate 2019/9/20
 */
@Slf4j
public class Product2 implements Runnable{
    private volatile boolean isRunning = true;
    /**
     * 内存缓冲区
     */
    private BlockingQueue<Integer> shareQueue;
    /**
     * 总数
     * 原子操作
     */
    private static AtomicInteger count = new AtomicInteger();
    private static final int SLEEPTIME = 1000;

    public Product2(BlockingQueue<Integer> shareQueue) {
        this.shareQueue = shareQueue;
    }

    @Override
    public void run() {
        int data;
        Random random = new Random();
        System.out.println("start product id:" + Thread.currentThread().getId());
        try {
            while (isRunning) {
                //模拟延迟
                Thread.sleep(random.nextInt(SLEEPTIME));

                //向阻塞队列中添加数据
                data = count.incrementAndGet();
                System.out.println("Productor " + Thread.currentThread().getId() + ", Size " + shareQueue.size());
                if (!shareQueue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("Failed to put data:" + data);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Exception:" + JSON.toJSONString(e));
            Thread.currentThread().isInterrupted();
        }
    }

    public void stop() {
        isRunning = false;
    }
}
