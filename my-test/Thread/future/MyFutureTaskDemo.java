package Thread.future;

import java.time.LocalDate;
import java.util.concurrent.*;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/2/12
 */
public class MyFutureTaskDemo {
    public static void testFutureTask1() {
        //要执行的任务
        MyCallable myCallable1 = new MyCallable(1000);
        MyCallable myCallable2 = new MyCallable(2000);

        //将Callable写的任务封装到一个由执行者调度的FutureTask对象
        FutureTask<String> futureTask1 = new FutureTask<>(myCallable1);
        FutureTask<String> futureTask2 = new FutureTask<>(myCallable2);

        //创建线程池并返回ExecutorService实例
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        //执行任务
        executorService.execute(futureTask1);
        executorService.execute(futureTask2);

        System.out.println("1:::" + System.currentTimeMillis());

        while (true) {
            try {
                //两个任务都完成
                System.out.println("2:::" + System.currentTimeMillis());
                if (futureTask1.isDone() && futureTask2.isDone()) {
                    System.out.println("Done!");
                    executorService.shutdown();
                    return;
                }

                //任务1未完成，会等待直到任务完成
                System.out.println("3:::" + System.currentTimeMillis());
                if (!futureTask1.isDone()) {
                    System.out.println("FutureTask1 output: " + futureTask1.get());
                }

                System.out.println("4:::" + System.currentTimeMillis());
                System.out.println("Waiting for FutureTask2 to complete!");
                String string = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if (string != null) {
                    System.out.println("FutureTask2 output: " + string);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * 父线程想要获取子线程的运行结果
     * 在父线程运行期间子线程也在运行，子线程在运行期间不会影响父线程的运行
     *
     * 使用场景:
     * 生产者消费者
     * 较复杂的操作数据库业务业务中，有查询数据库的结果作为后面步骤的条件的可使用
     */
    public static void testCallable() {
        CallableDemo1 callableDemo1 = new CallableDemo1();

        FutureTask<String> futureTask = new FutureTask<>(callableDemo1);

        new Thread(futureTask, "曹丕部队->").start();
        //设置父线程名
        Thread.currentThread().setName("曹操部队->");

        try {
            System.out.println(Thread.currentThread().getName() + "修整5s  " + System.currentTimeMillis());
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println(Thread.currentThread().getName() + "修整完毕!");

        try {
            String string = futureTask.get();
            System.out.println(Thread.currentThread().getName() + "获取友军消息" + string);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * testCallable的匿名内部类实现方法
     */
    public static void testCallable2() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                String th_name = Thread.currentThread().getName();
                System.out.println(th_name + "遭遇大规模敌军突袭..." + System.currentTimeMillis());
                System.out.println(th_name + "极速攻杀敌军...");
                return "敌军损失惨重，我军大获全胜";
            }
        };

        FutureTask<String> futureTask = new FutureTask<>(callable);

        new Thread(futureTask, "曹丕部队->").start();
        //设置父线程名
        Thread.currentThread().setName("曹操部队->");

        try {
            System.out.println(Thread.currentThread().getName() + "修整5s  " + System.currentTimeMillis());
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println(Thread.currentThread().getName() + "修整完毕!");

        try {
            String string = futureTask.get();
            System.out.println(Thread.currentThread().getName() + "获取友军消息 " + string);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //testFutureTask1();
        //testCallable();
        testCallable2();
    }
}
