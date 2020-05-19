package Java8;

import com.alibaba.fastjson.JSON;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/5/7
 */
public class Java8Main {
    public static void main(String[] args) {
        //test3();
        String str = "0000";
        System.out.println(str.replaceAll("0", "").length());
    }

    /**
     * 方法引用示例
     */
    public static void test1() {
        /**
         * 方法一:构造器引用，语法是Class::new,注意构造器是无参的
         *
         * zhangs 注: Car.create(Car::new)等价于Car.create(() -> new Car())
         */
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);
        System.out.println(JSON.toJSONString(cars));

        /**
         * 方法二:静态方法引用，它的语法是Class::static_method
         *
         * zhangs 注: Car::collide等价于car1 -> Car.collide(car1)即(Car car1) -> Car.collide(car1)
         */
        cars.forEach(Car::collide);
        System.out.println(JSON.toJSONString(cars));

        /**
         * 方法三:特定类的任意对象的方法引用，它的语法是Class::method
         */
        cars.forEach(Car::repair);
        System.out.println(JSON.toJSONString(cars));

        /**
         * 方法四:特定对象的方法引用，它的语法是instance::method
         */
        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);
        System.out.println(JSON.toJSONString(cars));
    }

    public static void test2() {
        Double[] doubles = new Double[] {17.5675, 12.4775, 11.47167, 11.31333, 18.345, 14.9425, 17.31333, 15.10917, 14.10833};
        Double[] averages = new Double[5];
        for (int i=0;i<doubles.length - 4;i++) {
            Double ave = (doubles[i+0] + doubles[i+1] + doubles[i+2] + doubles[i+3] + doubles[i+4]) / 5;
            /*System.out.println("AVE: " + ave);*/
            averages[i] = ave;
        }
        Double ave5 = (averages[0] + averages[1] + averages[2] + averages[3] + averages[4]) / 5;
        System.out.println("5年平均市盈率: " + ave5);

        Double[] rates = new Double[4];
        for (int i=0;i<averages.length - 1;i++) {
            rates[i] = Math.abs((averages[i+1] - averages[i]) * 100 / averages[i]);
        }
        Double rate5 = (rates[0] + rates[1] + rates[2] + rates[3]) / 4;
        System.out.println("5年平均市盈率波动率: " + rate5);

        /**
         * a. 当基准均线 – （T – 1）日的上证指数市盈率 ≥ 偏离阈值时，执行高档扣款       份
         * b. 当 │ （T – 1）日的上证指数市盈率 – 基准均线 │ < 偏离阈值时，执行中档扣款       份
         * c. 当 （T – 1）日的上证指数市盈率 – 基准均线 ≥ 偏离阈值时，执行低挡扣款       份
         */
        /*Double T1 = 12.66;    20200511*/
        //20171222
        Double T1 = 17.99;
        if ((ave5 - T1) >= rate5) {
            System.out.println("执行高档扣款");
        } else if ((Math.abs(T1 - ave5)) < rate5) {
            System.out.println("执行中档扣款");
        } else if ((T1 - ave5) >= rate5) {
            System.out.println("执行低挡扣款");
        }
    }

    public static void test3() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("123");
        list.add("456");
        System.out.println(JSON.toJSONString(list));
        Set<String> set = new HashSet<>(list);
        System.out.println(JSON.toJSONString(set));
    }

}
