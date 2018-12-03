package RandomUtil;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @author zhangs
 * @Description
 * @createDate 2018/11/5
 */
public class RandomUtil {
    public static void main(String[] args) {
//        int side = 2147483647;
//        double random = Math.random()*side;
//        System.out.println(random);
//        System.out.println((long) random);
//        System.out.println(Integer.MAX_VALUE);

//        System.out.println(UUID.randomUUID().toString().replace("-", ""));
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("abc");
        list.add("456");
        System.out.println(JSON.toJSONString(list));
        String[] strings = new String[list.size()];
        list.toArray(strings);
        System.out.println(JSON.toJSONString(strings));
    }
}
