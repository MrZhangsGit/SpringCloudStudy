import com.alibaba.fastjson.JSON;
import javafx.scene.control.Cell;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.concurrent.atomic.*;

/**
 * @author zhangs
 * @Description
 * @createDate 2018/11/21
 */
public class Test {
    /*public static void main(String[] args) {
        /*String project = UUID.randomUUID().toString().replace("-", "");
        System.out.println(project);
        System.out.println(project.substring(0, 10));*/

        /*List<Device> emqDataMOS = new ArrayList<>();
        Device emqDataMO = new Device();
        emqDataMO.setDeviceId("123456");
        emqDataMOS.add(emqDataMO);

        emqDataMO = new Device();
        emqDataMO.setDeviceId("987654");
        emqDataMOS.add(emqDataMO);

        emqDataMO = new Device();
        emqDataMO.setDeviceId("abcdef");
        emqDataMOS.add(emqDataMO);
        System.out.println(JSON.toJSONString(emqDataMOS));
        for (int i=0;i<emqDataMOS.size();i++) {
            emqDataMOS.get(i).setDeviceId("00000" + i);
        }
        System.out.println(JSON.toJSONString(emqDataMOS));*/

        /*List<String> deviceIds = new ArrayList<>();
        deviceIds.add("123456");
        deviceIds.add("789");
        deviceIds.add("abc");
        System.out.println(JSON.toJSONString(deviceIds));*/
        /*String projectKey = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        System.out.println(projectKey.length());*/

        /*System.out.println(UUID.randomUUID().toString().replace("-", ""));*/
        /*String user = "' or 1 = 1 -- ";
        System.out.println(":::" + user.replaceAll(".*([';]+|(--)+).*", " "));*/
        /*String content = "{1}";
        System.out.println(content.replaceAll("\\{", ""));
        System.out.println(content.replaceAll("\\}", ""));*/
        /*String content = "{}";
        System.out.println(SqlUtil.preventSQLInjection(content));*/
        /*Map<String, Object> groupMap = new HashMap<>();
        System.out.println(CollectionUtils.isEmpty(groupMap));*/
        /*Date time = new Date("2019/01/17 9:49:06");
        System.out.println(time);*/
        /*List<String> projectIds = new ArrayList<>();
        projectIds.add("23349d43fd0547ff");
        System.out.println(JSON.toJSONString(projectIds));*/
        /*Device device = new Device();
        if (StringUtils.isBlank(device.getDeviceId())) {
            System.out.println(123);
        }*/
        /*System.out.println("main:" + Thread.currentThread().getName());
        System.out.println("main:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        test();*/

        /*MyRunableThread myThread = new MyRunableThread();
        Thread t1 = new Thread(myThread);
        Thread t2 = new Thread(myThread);
        Thread t3 = new Thread(myThread);
        t1.start();
        t2.start();
        t3.start();
        new MyThread().start();*/
        /*final Count count = new Count();

        new Thread(new Runnable() {
            @Override
            public void run() {
                count.synMethod1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                count.synMethod2();
            }
        }).start();*
    }*/

    public static void test() {
        System.out.println("test:" + Thread.currentThread().getName());
        System.out.println("test:" + Thread.currentThread().getStackTrace()[1].getMethodName());
    }


}

class Demo {
    public synchronized void synMethod() {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + Thread.currentThread().getName());
        for (int i=0;i<1000000;i++){};
    }

    public void synBlock() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + Thread.currentThread().getName());
            for (int i=0;i<1000000;i++){};
        }
    }

    /*public static void main(String[] args) {
        Demo demo = new Demo();

        long start;
        start = System.currentTimeMillis();
        demo.synMethod();
        System.out.println("synMethod() " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        demo.synBlock();
        System.out.println("synBlock() " + (System.currentTimeMillis() - start));
    }*/
}

class Device {
    private String deviceId;

    public Device() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}

class MyRunableThread implements Runnable {

    private int ticket = 10;

    @Override
    public void run() {
        for(int i = 0;i<20;i++) {
            if (this.ticket > 0) {
                System.out.println(Thread.currentThread().getName() + " 卖票:ticket " + this.ticket--);
            }
        }
    }
}

class MyThread extends Thread {
    private int ticket = 10;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" 卖票：ticket "+this.ticket--);
        System.out.println(this.getName()+" 卖票：ticket "+this.ticket--);

        /*for(int i=0;i<20;i++){
            if(this.ticket>0){
                System.out.println(this.getName()+" 卖票：ticket "+this.ticket--);
                System.out.println(Thread.currentThread().getName()+" 卖票：ticket "+this.ticket--);
            }
        }*/
    }
}

class MyRunable implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0;i < 5;i++) {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " loop " + i);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

class Count {
    public void synMethod1() {
        synchronized (this) {
            try {
                for (int i=0;i<5;i++) {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " synMethod1 loop " + i);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void synMethod2() {
        synchronized (this) {
            try {
                for (int i=0;i<5;i++) {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " synMethod2 loop " + i);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

class Something {
    public synchronized void isSyncA() {
        try {
            for (int i=0;i<5;i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " : isSyncA");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public synchronized void isSyncB() {
        try {
            for (int i=0;i<5;i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " : isSyncB");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static synchronized void cSyncA() {
        try {
            for (int i=0;i<5;i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " : cSyncA");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static synchronized void cSyncB() {
        try {
            for (int i=0;i<5;i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " : cSyncB");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*public static void main(String[] args) {
        Something x = new Something();

        new Thread(new Runnable() {
            @Override
            public void run() {
                x.isSyncA();
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Something.cSyncA();
            }
        }, "t2").start();
    }*/
}

class MyThread2 extends Thread {
    public MyThread2(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (this) {
            System.out.println(System.currentTimeMillis() + "---" + Thread.currentThread().getName() + " call notify()");
            notify();
        }
    }

    /*public static void main(String[] args) {
        MyThread2 thread2 = new MyThread2("thread2");

        synchronized (thread2) {
            try {
                System.out.println(System.currentTimeMillis() + "---" + Thread.currentThread().getName() + " start thread2");
                thread2.start();

                System.out.println(System.currentTimeMillis() + "---" + Thread.currentThread().getName() + " wait");
                thread2.wait();

                System.out.println(System.currentTimeMillis() + "---" + Thread.currentThread().getName() + " continue");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }*/
}

class MyNotifyAll {

    private static Object obj = new Object();

    static class MyThread3 extends Thread {
        public MyThread3(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (obj) {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait");

                    obj.wait();

                    System.out.println(Thread.currentThread().getName() + " continue");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    /*public static void main(String[] args) {
        new MyThread3("t1").start();
        new MyThread3("t2").start();
        new MyThread3("t3").start();

        try {
            System.out.println(Thread.currentThread().getName() + " sleep(3000)");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e);
        }

        synchronized (obj) {
            System.out.println(Thread.currentThread().getName() + " notifyAll()");
            obj.notifyAll();
        }
    }*/
}

class MyThread4 extends Thread {
    public MyThread4(String name) {
        super(name);
    }

    @Override
    public synchronized void run() {
        for (int i=0;i<10;i++) {
            System.out.println(Thread.currentThread().getName() + "---" + this.getName() + "[" + this.getPriority() + "]:" + i);
            if (i%4 == 0) {
                System.out.println(Thread.currentThread().getName() + " yield");
                Thread.yield();
            }
        }
    }

    /*public static void main(String[] args) {
        new MyThread4("t1").start();
        new MyThread4("t2").start();
    }*/
}

class SleepLockTest {
    /*public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " (" + Thread.currentThread().getPriority() + ")");

        MyThread5 t1 = new MyThread5("t1");
        MyThread5 t2 = new MyThread5("t2");
        t1.setPriority(1);
        t2.setPriority(10);
        t1.start();
        t2.start();
    }*/
}

class MyThread5 extends Thread {
    public MyThread5(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i=0;i<5;i++) {
            System.out.println(Thread.currentThread().getName() + " (" + Thread.currentThread().getPriority() + ") " + " loop " + i);
        }
    }
}

class Depot {
    /**
     * 仓库容量
     */
    private int capacity;

    /**
     * 仓库的实际容量
     */
    private int size;

    public Depot(int capacity) {
        this.capacity = capacity;
        this.size = 0;
    }

    public synchronized void produce(int val) {
        try {
            /**
             * left 标识想要生成的数量(可能需要的量较大要多次生产)
             */
            int left = val;
            while (left > 0) {
                /**
                 * 库存已满时等待消费者消费产品
                 */
                while (size >= capacity) {
                    wait();
                }

                /**
                 * 获取实际生产的数量(即库存中新增的数量)
                 * 若库存+想要生产的数量>总容量capacity，则实际增量=总容量-库存，此时满仓
                 * 否则实际增量=想要生产的数量
                 */
                int inc = (size + left) > capacity ? (capacity - size) : left;
                size += inc;
                left -= inc;
                System.out.printf("%s produce(%3d) --> left=%3d, inc=%3d, size=%3d \n", Thread.currentThread().getName(),
                        val, left, inc, size);
                notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void consume(int val) {
        try {
            /**
             * left 表示消费者要消费的数量(可能消费的量过大，库存不够，需多次消费)
             */
            int left = val;
            while (left > 0) {
                /**
                 * 库存为0时，等待生产者生产
                 */
                while (size <= 0) {
                    wait();
                }

                /**
                 * 获取实际消费数量(即库存中实际减少的数量)
                 * 若库存<消费者要消费的数量，则实际消费量=库存
                 * 否则实际消费量=消费者要消费的数量
                 */
                int dec = (size < left) ? size : left;
                size -= dec;
                left -= dec;
                System.out.printf("%s consume(%3d) <-- left=%3d, dec=%3d, size=%3d \n", Thread.currentThread().getName(),
                        val, left, dec, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "capacity:" + capacity + ", actual size=" + size;
    }
}

class Producer {
    private Depot depot;

    public Producer(Depot depot) {
        this.depot = depot;
    }

    public void produce(final int val) {
        new Thread() {
            @Override
            public void run() {
                depot.produce(val);
            }
        }.start();
    }
}

class Consumer {
    private Depot depot;

    public Consumer(Depot depot) {
        this.depot = depot;
    }

    public void consume(final int val) {
        new Thread() {
            @Override
            public void run() {
                depot.consume(val);
            }
        }.start();
    }
}

class Person {
    volatile long id;

    public Person(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id:" + id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

class AtomicDemo {

    public static void AtomicLongFieldUpdateTest() {
        Class cls = Person.class;

        AtomicLongFieldUpdater atomicLongFieldUpdater = AtomicLongFieldUpdater.newUpdater(cls, "id");
        Person person = new Person(12345678L);

        atomicLongFieldUpdater.compareAndSet(person, 12345678L, 1000);
        System.out.println("id:" + person.getId());
    }

    public static void AtomaticReferenceTest() {
        Person p1 = new Person(101);
        Person p2 = new Person(102);

        AtomicReference ar = new AtomicReference(p1);
        ar.compareAndSet(p1, p2);

        Person p3 = (Person) ar.get();
        System.out.println("p3 is:" + p3);
        System.out.println("p3.equals(p1)=" + p3.equals(p1));
    }

    public static void atomicLongArr() {
        long[] arrLong = new long[] {10, 20, 30, 40, 50};
        AtomicLongArray ala = new AtomicLongArray(arrLong);

        ala.set(0, 100);
        for (int i=0;i<ala.length();i++) {
            System.out.printf("get(%d) : %s \n", i, ala.get(i));
        }

        System.out.printf("%20s : %s \n", "getAndDecrement(0)", ala.getAndDecrement(0));
        System.out.printf("%20s : %s \n", "decrementAndGet(1)", ala.decrementAndGet(1));
        System.out.printf("%20s : %s \n", "getAndIncrement(2)", ala.getAndIncrement(2));
        System.out.printf("%20s : %s \n", "incrementAndGet(3)", ala.incrementAndGet(3));

        System.out.printf("%20s : %s \n", "addAndGet(100)", ala.addAndGet(0, 100));
        System.out.printf("%20s : %s \n", "getAndAdd(100)", ala.getAndAdd(1, 100));

        System.out.printf("%20s : %s \n", "compareAndSet()", ala.compareAndSet(2, 31, 1000));
        System.out.printf("%20s : %s \n", "getAndDecrement(0)", ala.get(2));
    }

    public static void atomicLong() {
        AtomicLong atomicLong = new AtomicLong();

        atomicLong.set(0x0123456789ABCDEFL);
        /**
         * 获取当前值
         */
        System.out.printf("%20s : 0x%016X \n", "get()", atomicLong.get());
        /**
         * 获取当前值对应的int值
         */
        System.out.printf("%20s : 0x%016X \n", "intValue()", atomicLong.intValue());
        /**
         * 获取当前值对应的long值
         */
        System.out.printf("%20s : 0x%016X \n", "longValue()", atomicLong.longValue());
        System.out.printf("%20s : %s \n", "doubleValue()", atomicLong.doubleValue());
        System.out.printf("%20s : %s \n", "floatValue()", atomicLong.floatValue());

        /**
         * 以原子方式将当前值减1(i--)
         */
        System.out.printf("%20s : 0x%016X \n", "getAndDecrement()", atomicLong.getAndDecrement());
        /**
         * 以原子方式将当前值间1(--i)
         */
        System.out.printf("%20s : 0x%016X \n", "decrementAndGet()", atomicLong.decrementAndGet());
        /**
         * (i++)
         */
        System.out.printf("%20s : 0x%016X \n", "getAndIncrement()", atomicLong.getAndIncrement());
        /**
         * (++i)
         */
        System.out.printf("%20s : 0x%016X \n", "incrementAndGet()", atomicLong.incrementAndGet());

        /**
         * 以原子方式将delta与当前值相加并返回相加后的值
         */
        System.out.printf("%20s : 0x%016X \n", "addAndGet(0x10)", atomicLong.addAndGet(0x10));
        /**
         * 以原子方式将delta与当前值相加并返回相加前的值
         */
        System.out.printf("%20s : 0x%016X \n", "getAndAdd(0x10)", atomicLong.getAndAdd(0x10));

        System.out.printf("\n%20s : 0x%016X \n", "get()", atomicLong.get());

        /**
         * 若当前值==expect，则以原子方式将该值设置为update。成功返回true，否则false且不修改原值
         */
        System.out.printf("%20s : %s \n", "", atomicLong.compareAndSet(0x123456789L, 0xFEDCBA9876543210L));
        System.out.printf("%20s : 0x%016X \n", "get()", atomicLong.get());
    }

    public static void main(String[] args) {
        AtomicLongFieldUpdateTest();
    }
}