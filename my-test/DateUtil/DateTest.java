package DateUtil;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/4/9
 */
public class DateTest {
    /**
     * SimpleDateFormat崩溃测试
     *      在多线程下就会崩溃。SimpleDateFormat定义为静态变量，多线程下SimpleDateFormat的实例会被多个线程共享，
     *      部分线程获取的时间不对导致出现异常
     * SimpleDateFormat并不是一个线程安全的类
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(LocalDateTime date){
        return formatter.format(date);
    }

    public static LocalDateTime parse(String strDate){
        return LocalDateTime.parse(strDate, formatter);
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(100);
        for (int i=0;i<20;i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j=0;j<10;j++) {
                        try {
                            System.out.println(formatter.format(LocalDateTime.now()));
                            System.out.println(parse("2018-01-02 09:30:59"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
    }
}
