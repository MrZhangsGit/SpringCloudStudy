package PoolDemo;

import org.apache.commons.pool.PoolableObjectFactory;

public class ObjectPoolFactory implements PoolableObjectFactory {
    private Class cls;
    //有状态对象恢复初始化的方法
    private static final String INIT_METHOD = "clearObject";

    public ObjectPoolFactory(Class cls) {
        this.cls = cls;
    }

    @Override
    public void activateObject(Object object) throws Exception {
        System.out.println("---有状态对象恢复初始化---");
        try {
            cls.getDeclaredMethod(INIT_METHOD).invoke(object);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Object makeObject() throws Exception {
        System.out.println("---创建新对象---");
        return cls.newInstance();
    }

    @Override
    public void destroyObject(Object o) throws Exception {

    }

    @Override
    public boolean validateObject(Object o) {
        return false;
    }

    @Override
    public void passivateObject(Object o) throws Exception {

    }
}
