import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhangs
 * @Description
 * @createDate 2018/11/29
 */
public class Main {
    private static final List<Object> TEST_DATA = new LinkedList<>();

    private static final ReferenceQueue<Sample> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {

        //创建一个对象，new出来的对象都是分配在java堆中的,sample这个引用就是强引用
        Sample sample = new Sample();

        //创建一个软引用指向这个对象   那么此时就有两个引用指向Sample对象
        SoftReference<Sample> softRef = new SoftReference<Sample>(sample, QUEUE);

        //将强引用指向空指针 那么此时只有一个软引用指向Sample对象
        sample = null;

        new Thread(){
            @Override
            public void run() {
                System.out.println("-----1");
                while (true) {
                    System.out.println(softRef.get());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    TEST_DATA.add(new byte[1024 * 1024 * 5]);
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                System.out.println("-----2");
                while (true) {
                    Reference<? extends Sample> poll = QUEUE.poll();
                    if (poll != null) {
                        System.out.println("--- 软引用对象被jvm回收了 ---- " + poll);
                        System.out.println("--- 回收对象 ---- " + poll.get());
                    }
                }
            }
        }.start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.exit(1);
        }
    }
}

class Sample {
    private final byte[] data;

    public Sample() {
        data = new byte[1024 * 1024 * 10];
    }
}
