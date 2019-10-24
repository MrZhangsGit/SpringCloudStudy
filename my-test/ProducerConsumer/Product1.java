package ProducerConsumer;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangs
 * @Description 利用wait()/notifyAll()实现生产者-消费者
 * @createDate 2019/9/20
 */
@Slf4j
public class Product1 implements Runnable{
    private volatile boolean isRunning = true;
    /**
     * 内存缓冲区
     */
    private final Vector shareQueue;
    /**
     * 缓冲区大小
     */
    private final int SIZE;
    /**
     * 总数
     * 原子操作
     */
    private static AtomicInteger count = new AtomicInteger();
    private static final int SLEEPTIME = 1000;


    public Product1(Vector shareQueue, int SIZE) {
        this.shareQueue = shareQueue;
        this.SIZE = SIZE;
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

                //当前队列满时阻塞等待
                while (shareQueue.size() == SIZE) {
                    synchronized (shareQueue) {
                        log.info("Queue is Full! product {} is waiting, SIZE {}", Thread.currentThread().getId(),
                                shareQueue.size());
                        shareQueue.wait();
                    }
                }

                //队列不满时保持创造新元素
                synchronized (shareQueue) {
                    /**
                     * 构造任务数据
                     */
                    data = count.incrementAndGet();
                    shareQueue.add(data);
                    System.out.println("Product create data:" + data + ", SIZE " + shareQueue.size());
                    shareQueue.notifyAll();
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
