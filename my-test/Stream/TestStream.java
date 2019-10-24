package Stream;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.ast.Str;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author zhangs
 * @Description java8中的Stream
 * @createDate 2019/9/26
 */
public class TestStream {
    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("abc");
        list.add("456");
        list.parallelStream().forEach(System.out::println);
        Stream.of(list).forEach(System.out::println);
    }

    @Test
    public void testCombination() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        for (int i=0;i<list.size();i++) {
            for (int j=0;j<list.size();j++) {
                if (j==i) {
                    continue;
                }
                System.out.println(list.get(i) + ":" + list.get(j));
            }
        }
    }

    @Test
    public void testMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        System.out.println(JSON.toJSONString(map));
    }
}
