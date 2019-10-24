package ProducerConsumer;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

/**
 * @author zhangs
 * @Description 利用wBlockingQueue阻塞队列方式实现生产者-消费者
 * @createDate 2019/9/20
 */
@Slf4j
public class Consumer2 implements Runnable{
    /**
     * 内存缓冲区
     */
    private final BlockingQueue<Integer> shareQueue;

    private static final int SLEEPTIME = 1000;

    public Consumer2(BlockingQueue<Integer> shareQueue) {
        this.shareQueue = shareQueue;
    }

    @Override
    public void run() {
        Random random = new Random();
        int data;

        System.out.println("start consumer id " + Thread.currentThread().getId());
        try {
            while (true) {
                //模拟延迟
                Thread.sleep(random.nextInt(SLEEPTIME));

                //从阻塞队列中获取数据
                if (!shareQueue.isEmpty()) {
                    data = shareQueue.take();
                    System.out.println("Consumer " + Thread.currentThread().getId() + ", Size:" + shareQueue.size());
                } else {
                    System.out.println("Queue is empty! Consumer " + Thread.currentThread().getId() + " is waiting, Size:" + shareQueue.size());
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);
            Thread.currentThread().interrupt();
        }
    }
}
