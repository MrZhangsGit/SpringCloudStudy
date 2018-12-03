package PoolDemo;

import com.alibaba.fastjson.JSON;
import org.apache.commons.pool.impl.GenericObjectPool;

import java.util.ArrayList;
import java.util.List;

public class ObjectPool {
    private GenericObjectPool pool;

    public ObjectPool(Class cls) {
        this.pool = new GenericObjectPool(new ObjectPoolFactory(cls));
        //最大活动对象
        pool.setMaxActive(4);
        //最大空闲对象
        pool.setMaxIdle(1);
        //最大等等时间
        pool.setMaxWait(100000);
    }

    /**
     * 从池中取出对象
     */
    public <T> T borrowObject() {
        T object = null;
        try {
            object = (T) pool.borrowObject();
            System.out.println("---获取对象---");
        } catch (Exception e) {
            System.out.println("BORROW_OBJECT_ERROR!" + e);
        }
        System.out.println("池中对象总数:" + (pool.getNumActive() + pool.getNumIdle()) + "; 使用中:" + pool.getNumActive() +
                "; 空闲中:" + pool.getNumIdle());
        return object;
    }

    /**
     * 对象放回池中
     */
    public void returnObject(Object object) {
        try {
            pool.returnObject(object);
            System.out.println("---返还对象---");
        } catch (Exception e) {
            System.out.println("RETURN_OBJECT_ERROR!" + e);
        }
    }
}

class Test {
    public static ObjectPool pool = new ObjectPool(Object.class);

    public static void main(String[] args) {
        for (int i = 0;i < 20;i++) {
            new Thread() {
                @Override
                public void run() {
                    Object object = Test.pool.borrowObject();
                    System.out.println("---object:" + object.toString());
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("---" + e);
                    }
                    Test.pool.returnObject(object);
                }
            }.start();
        }
    }
}
