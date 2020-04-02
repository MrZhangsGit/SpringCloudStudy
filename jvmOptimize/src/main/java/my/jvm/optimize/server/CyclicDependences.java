package my.jvm.optimize.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangs
 * @Description
 * 准备模拟内存泄漏样例
 * 1、定义静态变量HashMap
 * 2、分段循环创建对象，并加入HashMap
 * @createDate 2020/3/26
 */
@Slf4j
@Component
public class CyclicDependences{
    private static final Map map = new HashMap();

    public void run() {
        log.info("start...");
        /**
         * 给打开jvisualvm时间
         */
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            log.error("{}", e);
        }

        for (int i=0;i<100000;i++) {
            TestMemory testMemory = new TestMemory();
            map.put("key:" + i, testMemory);
        }
        log.info("first...");
        /**
         * 为dump出堆提供时间
         */
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            log.error("{}", e);
        }

        for (int i=0;i<100000;i++) {
            TestMemory testMemory = new TestMemory();
            map.put("key:" + i, testMemory);
        }
        log.info("second...");

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            log.error("{}", e);
        }
        for (int i=0;i<100000;i++) {
            TestMemory testMemory = new TestMemory();
            map.put("key:" + i, testMemory);
        }
        log.info("third...");

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            log.error("{}", e);
        }
        for (int i=0;i<100000;i++) {
            TestMemory testMemory = new TestMemory();
            map.put("key:" + i, testMemory);
        }
        log.info("forth...");

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            log.error("{}", e);
        }
        log.info("end...");
    }
}

class TestMemory {
    private String str;

    public TestMemory() {
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
