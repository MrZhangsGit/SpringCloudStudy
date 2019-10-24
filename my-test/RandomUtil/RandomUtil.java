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
        /*int side = 2147483647;
        double random = Math.random()*side;
        System.out.println(random);
        System.out.println((long) random);
        System.out.println(Integer.MAX_VALUE);*/

        for (int i=0;i<8;i++) {
            System.out.println(UUID.randomUUID().toString().replace("-", ""));
        }
    }
}
