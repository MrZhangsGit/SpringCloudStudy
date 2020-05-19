package Java8;

import java.util.function.Supplier;

/**
 * @author zhangs
 * @Description 方法引用
 * @createDate 2020/5/7
 */
public class Car {
    public static Car create(final Supplier<Car> supplier) {
        /**
         * Supplier:一个提供结果的函数接口
         * 创建Supplier容器，此时并不会调用对象的构造方法，即不会创建对象
         * 调用get()方法，此时会调用对象的构造方法，即获得到真正对象,并且每次调用创建的对象都不一样
         */
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }
}
