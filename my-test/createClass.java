import lombok.Data;

import java.lang.reflect.Constructor;

/**
 * @author zhangs
 * @Description 创建对象的方法
 * @createDate 2020/4/17
 */
public class createClass {
    public static void main(String[] args) {
        test4();
    }

    public static void test1() {
        Phone phone = new Phone();
        phone.setType("安卓");
        System.out.println(phone.getType());
    }

    /**
     * Class.forName("")返回的是类
     * Class.forName("").newInstance()返回的是object
     * Class下的newInstance()的使用有局限，因为它生成对象只能调用无参的构造函数.
     *   new是一个关键字，使用new关键字生成对象没有构造函数有无参的限制
     */
    public static void test2() {
        try {
            Phone phone = (Phone) Class.forName("Phone").newInstance();
            phone.setType("安卓");
            System.out.println(phone.getType());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 通过类对象的getConstructor()或getDeclaredConstructor()方法获得构造器（Constructor）对象
     * 并调用其newInstance()方法创建对象，适用于无参和有参构造方法。
     *
     * getDeclaredConstructor(Class<?>... parameterTypes)
     * 这个方法会返回制定参数类型的所有构造器，包括public的和非public的，当然也包括private的。
     * getDeclaredConstructors()的返回结果就没有参数类型的过滤了。
     *
     * getConstructor(Class<?>... parameterTypes)
     * 这个方法返回的是上面那个方法返回结果的子集，只返回制定参数类型访问权限是public的构造器。
     * getConstructors()的返回结果同样也没有参数类型的过滤。
     */
    public static void test3() {
        try {
            Class cls = Class.forName("Phone");
            //调用无参构造函数
            Constructor constructor = cls.getDeclaredConstructor();
            Phone phone = (Phone) constructor.newInstance();
            phone.setType("安卓");
            System.out.println("Constructor调用无参构造函数:" + phone.getType());
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            Class cls = Class.forName("Phone");
            //调用有参构造函数
            Constructor constructor2 = cls.getConstructor(new Class[]{String.class});
            Phone phone2 = (Phone) constructor2.newInstance("苹果");
            phone2.setName("IPhone");
            System.out.println("Constructor调用有参构造函数:" + phone2.getType() + phone2.getName());
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            Class cls = Class.forName("Phone");
            //调用私有有参构造函数
            Constructor constructor3 = cls.getDeclaredConstructor(new Class[]{String.class, String.class});
            constructor3.setAccessible(true);
            Phone phone3 = (Phone) constructor3.newInstance("塞班", "诺基亚");
            System.out.println("Constructor私有调用有参构造函数:" + phone3.getType() + phone3.getName());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void test4() {
        try {
            Phone phone = new Phone();
            phone.setType("安卓");
            System.out.println("Clone 原对象" + phone.getType());

            Phone phone1 = phone.clone();
            phone1.setType("苹果");
            System.out.println("Clone clone对象" + phone1.getType());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class Phone implements Cloneable{
    private String type;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Phone() {
    }

    public Phone(String type) {
        this.type = type;
    }

    private Phone(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    protected Phone clone() throws CloneNotSupportedException {
        return (Phone)super.clone();
    }
}
