package ProducerConsumer;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Vector;

/**
 * @author zhangs
 * @Description 利用wait()/notifyAll()实现生产者-消费者
 * @createDate 2019/9/20
 */
@Slf4j
public class Consumer1 implements Runnable{
    /**
     * 内存缓冲区
     */
    private final Vector shareQueue;

    /**
     * 缓冲区大小
     */
    private final int SIZE;
    private static final int SLEEPTIME = 1000;

    public Consumer1(Vector shareQueue, int SIZE) {
        this.shareQueue = shareQueue;
        this.SIZE = SIZE;
    }

    @Override
    public void run() {
        Random random = new Random();

        System.out.println("start consumer id " + Thread.currentThread().getId());
        try {
            while (true) {
                //模拟延迟
                Thread.sleep(random.nextInt(SLEEPTIME));

                //当队列空时阻塞等待
                while (shareQueue.isEmpty()) {
                    synchronized (shareQueue) {
                        System.out.println("Queue is empty, consumer " + Thread.currentThread().getId() +
                                " is waiting, size " + shareQueue.size());
                        shareQueue.wait();
                    }
                }

                //队列不空是持续消费元素
                synchronized (shareQueue) {
                    System.out.println("Consumer consume data " + shareQueue.remove(0) + ", size " + shareQueue.size());
                    shareQueue.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);
            Thread.currentThread().interrupt();
        }
    }
}
