package PoolDemo;

import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 线程池中有两个集合，一个集合A存放空闲中的对象，一个集合B存放使用中的对象，
 * 线程从A中拿到对象使用，并放入B中，当线程使用完对象之后从B中取出放回A中，
 * 有状态的对象在使用之前先恢复为初始化状态，
 * 当线程池中所有的对象都是使用中时（即都属于集合B），线程等待，当有线程返还对象时，线程唤醒。
 * @param <E>
 */
public class MyRoughObjectPool<E> {
    /**
     * 正被使用的对象集合，已被同步
     */
    private Set<Object> activeSet = Collections.synchronizedSet(new HashSet<Object>());
    /**
     * 空闲对象集合，已被同步
     */
    private Set<Object> idleSet = Collections.synchronizedSet(new HashSet<Object>());

    /**
     * 最大对象数
     */
    private Integer maxObject;
    /**
     * 对象池的类
     */
    private Class<E> cls;
    /**
     * 线程等待监视器
     */
    private Object lock = new Object();

    /**
     * 构造方法
     * @param maxObject
     * @param cls
     */
    public MyRoughObjectPool(Integer maxObject, Class<E> cls) {
        this.maxObject = maxObject;
        this.cls = cls;
    }

    /**
     * 从池中取出对象
     */
    public synchronized <E> E getObject() throws Exception {
        Object object = null;
        if (!CollectionUtils.isEmpty(idleSet)) {
            Iterator<Object> iterator = idleSet.iterator();
            object = iterator.next();
        }
        if (object != null) {
            idleSet.remove(object);
            activeSet.add(object);
        } else {
            int size = activeSet.size() + idleSet.size();
            if (size >= maxObject) {
                synchronized (lock) {
                    System.out.println("---池中无可用对象，线程等待---");
                    lock.wait();
                }
                return getObject();
            } else {
                object = cls.newInstance();
                activeSet.add(object);
            }
        }
        System.out.println("池中对象总数:" + (activeSet.size() + idleSet.size()) + "; 使用中:" + activeSet.size() +
                "; 空闲中:" + idleSet.size());
        /**
         * 有状态对象恢复默认初始化
         */
        clearObject(object);
        return (E)object;
    }

    /**
     * 对象使用完毕，返回池
     */
    public void returnObject(Object object) {
        if (object != null) {
            activeSet.remove(object);
            idleSet.add(object);
            synchronized (lock) {
                System.out.println("---唤醒等待线程---");
                lock.notify();
            }
        }
    }

    /**
     * 有状态对象恢复默认初始化
     */
    public void clearObject(Object object) throws Exception {
        Class<?> cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field:fields) {
            //对象中字段的修饰为private时使用true
            field.setAccessible(true);
            //对对象进行初始化
            field.set(object, null);
        }
    }

    public static void main(String[] args) {
        //初始化池
        int poolMax = 10;
        MyRoughObjectPool<Object> pool = new MyRoughObjectPool<Object>(poolMax, Object.class);

        //自定义运行线程
        class TestThread extends Thread{
            MyRoughObjectPool objectPool;

            public TestThread(MyRoughObjectPool objectPool) {
                this.objectPool = objectPool;
            }

            @Override
            public void run() {
                try {
                    /**
                     * 从池中取出一个对象
                     * 工作1秒
                     * 将对象返还给池
                     */
                    Object object = objectPool.getObject();
                    Thread.sleep(1000);
                    objectPool.returnObject(object);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        //任务数
        poolMax = poolMax * 2;
        for (int i = 0;i < poolMax;i++) {
            System.out.println("---poolMax:" + poolMax + "; ---i:" + i);
            new TestThread(pool).start();
        }
    }
}
