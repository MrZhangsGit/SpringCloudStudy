import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author zhangs
 * @Description 布隆过滤器
 *      测试可得100w个数据中只消耗了约0.2毫秒就匹配到了key，速度足够快。
 *      然后模拟了1w个不存在于布隆过滤器中的key，出错率大概为3%，跟踪下BloomFilter的源码发现默认的容错率就是0.03。
 *      可调用BloomFilter的create方法显式的指定误判率,误判率0.03是设计者权衡系统性能后得出的值。
 *      要注意的是，布隆过滤器不支持删除操作。
 * @createDate 2020/3/20
 */
public class BloomFilterTest {
    /*容积定义*/
    private static final int capacity = 1000000;
    private static final int key = 999998;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), capacity);

    static {
        for (int i = 0; i < capacity; i++) {
            bloomFilter.put(i);
        }
    }

    public static void main(String[] args) {
        /*返回计算机最精确的时间，单位微妙*/
        long start = System.nanoTime();

        if (bloomFilter.mightContain(key)) {
            System.out.println("成功过滤到 " + key);
        }
        long end = System.nanoTime();
        System.out.println("布隆过滤器消耗时间:" + (end - start)/1000 + "ms");
        int sum = 0;
        for (int i = capacity + 20000; i < capacity + 30000; i++) {
            if (bloomFilter.mightContain(i)) {
                sum = sum + 1;
            }
        }
        System.out.println("错判率为:" + sum);
    }
}
