package OptionalDemo;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/6/3
 */
public class OptionalTest {
    public User createNewUser() {
        System.out.println("Create New User");
        User user = new User();
        user.setMail("123456@qq.com");
        user.setName("123456");
        return user;
    }

    @Test
    public void testWithNull() {
        User user = null;
        System.out.println("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());

        System.out.println("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
    }

    @Test
    public void testWithNotNull() {
        User user = new User();
        user.setMail("654321@qq.com");
        user.setName("654321");
        System.out.println("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());

        System.out.println("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(this::createNewUser);
    }

    @Test
    public void testMapDemo() {
        User user = new User();
        user.setMail("123456@qq.com");
        user.setName("123456");
        String mail = Optional.ofNullable(user).map(u -> u.getMail()).orElse("@qq.com");
        System.out.println(mail);
    }

    @Test
    public void testMapDemo2() {
        User user = new User();
        user.setTime(123L);
        Optional<User> u = Optional.ofNullable(user);
        Optional<Long> time = u.map(user1 -> user1.getTime());
        System.out.println(JSON.toJSONString(time.get()));
    }

    @Test
    public void testFlatMapDemo() {
        User user = new User("123456@qq.com", "123456", "Dev");
        String position = Optional.ofNullable(user).flatMap(u -> u.getPosition()).orElse("default");
        System.out.println(position);
    }

    @Test
    public void testFilter() {
        User user = new User("123456@qq.com", "123456", "Dev");
        Optional<User> result = Optional.ofNullable(user).
                filter(u -> StringUtils.isNotEmpty(u.getMail()) && u.getMail().contains("@"));
        System.out.println(JSON.toJSONString(result));
        System.out.println(JSON.toJSONString(result.get()));
    }

    @Test
    public void testOptional() {
        User user = new User("123456@qq.com", "123456", "Dev");

        String result = Optional.ofNullable(user)
                .flatMap(u -> u.getAddress())
                .flatMap(address -> address.getCountry())
                .map(country -> country.getIsocode())
                .orElse("default");
        System.out.println(result);
    }

    @Test
    public void testOptional2() {
        User user = new User("123456@qq.com", "123456", "Dev");
        String result = Optional.ofNullable(user)
                .flatMap(User::getAddress)
                .flatMap(Address::getCountry)
                .map(Country::getIsocode)
                .orElse("default");
        System.out.println(result);
    }
}
