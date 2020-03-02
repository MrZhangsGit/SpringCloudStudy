package Thread.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/1/16
 */
public class CallableTest implements Callable {

    @Override
    public Object call() throws Exception {
        int i=0;
        for (;i<10;i++) {
            System.out.println("当前线程是：" + Thread.currentThread() + ":" + i);
        }
        return i;
    }

    @Test
    public void test1() {
        CallableTest callableTest = new CallableTest();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callableTest);
        Thread thread = new Thread(futureTask, "I Am futureTask Thread");

        for (int i=0;i<5;i++) {
            System.out.println(Thread.currentThread().getName() + " 当前线程");
            if (i==1) {
                thread.start();
            }
        }

        try {
            System.out.println("返回值：" + futureTask.get());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void test2() {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int i=0;
                for (;i<10;i++) {
                    System.out.println("当前线程是：" + Thread.currentThread() + ":" + i);
                }
                return i;
            }
        });

        for (int i=0;i<5;i++) {
            System.out.println(Thread.currentThread().getName() + " 当前线程");
            if (i==1) {
                Thread thread = new Thread(futureTask, "I Am futureTask Thread");
                thread.start();
            }
        }

        try {
            System.out.println("返回值：" + futureTask.get());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
