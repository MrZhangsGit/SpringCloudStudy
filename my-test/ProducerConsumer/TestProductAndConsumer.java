package ProducerConsumer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/9/20
 */
public class TestProductAndConsumer {
    @Test
    public void test1() throws InterruptedException{
        /**
         * 构建内存缓冲区
         */
        Vector shareQueue = new Vector();
        int size = 4;

        /**
         * 建立线程池和线程
         */
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        int threadSize = 6;
        ExecutorService executorService = new ThreadPoolExecutor(threadSize,threadSize,
                0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),namedThreadFactory);

        ExecutorService service = Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(2);
        Executors.newSingleThreadExecutor();
        Executors.newScheduledThreadPool(2);
        Product1 prodThread1 = new Product1(shareQueue, size);
        Product1 prodThread2 = new Product1(shareQueue, size);
        Product1 prodThread3 = new Product1(shareQueue, size);
        Consumer1 consThread1 = new Consumer1(shareQueue, size);
        Consumer1 consThread2 = new Consumer1(shareQueue, size);
        Consumer1 consThread3 = new Consumer1(shareQueue, size);

        executorService.execute(prodThread1);
        executorService.execute(prodThread2);
        executorService.execute(prodThread3);
        executorService.execute(consThread1);
        executorService.execute(consThread2);
        executorService.execute(consThread3);

        Thread.sleep(10 * 1000);
        prodThread1.stop();
        prodThread2.stop();
        prodThread3.stop();

        Thread.sleep(3 * 1000);
        executorService.shutdown();
    }

    @Test
    public void test2() throws InterruptedException{
        /**
         * 构建内存缓冲区
         */
        BlockingQueue<Integer> shareQueue = new LinkedBlockingDeque<>();

        /**
         * 建立线程池和线程
         */
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        int threadSize = 6;
        ExecutorService executorService = new ThreadPoolExecutor(threadSize,threadSize,
                0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),namedThreadFactory);

        Product2 prodThread1 = new Product2(shareQueue);
        Product2 prodThread2 = new Product2(shareQueue);
        Product2 prodThread3 = new Product2(shareQueue);
        Consumer2 consThread1 = new Consumer2(shareQueue);
        Consumer2 consThread2 = new Consumer2(shareQueue);
        Consumer2 consThread3 = new Consumer2(shareQueue);

        executorService.execute(prodThread1);
        executorService.execute(prodThread2);
        executorService.execute(prodThread3);
        executorService.execute(consThread1);
        executorService.execute(consThread2);
        executorService.execute(consThread3);

        Thread.sleep(10 * 1000);
        prodThread1.stop();
        prodThread2.stop();
        prodThread3.stop();

        Thread.sleep(3 * 1000);
        executorService.shutdown();

    }
}
