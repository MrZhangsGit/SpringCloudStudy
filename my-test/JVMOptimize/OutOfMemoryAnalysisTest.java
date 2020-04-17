package JVMOptimize;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangs
 * @Description 内存溢出分析测试
 *  -XX:+HeapDumpOnOutOfMemoryError
 *  -Xms40m -Xmx40m
 * @createDate 2020/4/13
 */
public class OutOfMemoryAnalysisTest {
    public static void main(String[] args) {
        List<OutOfMemoryAnalysisDemo> list = new ArrayList<>();
        while (true) {
            list.add(new OutOfMemoryAnalysisDemo());
        }
    }
}

class OutOfMemoryAnalysisDemo {
    public OutOfMemoryAnalysisDemo() {
    }
}
