package DateUtil;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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

    /**
     * 时间转换
     * @param
     * @return
     * @author zhangs
     * @createDate 2019/6/25
     */
    @Test
    public void compatibleActiveDate() {
        Integer activeDateType = 3;
        String activeDate = "10:11";
        if (activeDateType == null || StringUtils.isEmpty(activeDate)) {
            return;
        }
        int count = 0;
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy").parse("1970"));
        } catch (Exception e) {
            System.out.println();
        }
        for (;activeDateType > 0;) {
            ActiveDateFormatEnum activeDateFormatEnum = ActiveDateFormatEnum.getEnum(activeDateType);
            if (activeDateFormatEnum == null) {
                break;
            }
            String activeDateFormat = activeDateFormatEnum.getTypeName();
            try {
                if (StringUtils.isEmpty(activeDate)) {
                    break;
                }
                Date date = new SimpleDateFormat(activeDateFormat).parse(activeDate);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                calendar.set(activeDateFormatEnum.getField(), cal.get(activeDateFormatEnum.getField()));
                System.out.println("1---:" + calendar.get(activeDateFormatEnum.getField()));
                count++;
                --activeDateType;
                if (activeDate.indexOf("-") > 0) {
                    activeDate = activeDate.substring(activeDate.indexOf("-")+1, activeDate.length());
                } else {
                    if (activeDate.indexOf(" ") > 0) {
                        activeDate = activeDate.substring(activeDate.indexOf(" ")+1, activeDate.length());
                    } else {
                        if (activeDate.indexOf(":") > 0) {
                            activeDate = activeDate.substring(activeDate.indexOf(":")+1, activeDate.length());
                        } else {
                            activeDate = null;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("解析失败");
               /* log.info("{}::APP接口兼容转换处理--兼容APP端传进来的有效时间并转换 解析失败！::" +
                                "activeDateType::{}, activeDate::{}, activeDateFormatEnum::{}, e::{}",
                        Thread.currentThread().getStackTrace()[1].getMethodName(), activeDateType, activeDate,
                        JSON.toJSONString(activeDateFormatEnum), JSON.toJSONString(e));*/
                break;
            }
            System.out.println("2---:" + calendar.getTime());
        }
        if (count > 0) {
            System.out.println(calendar.getTime());
            return;
        }
        return;
    }

    /**
     * 兼容APP端传进来的有效时间并转换(String->Date)
     * @param
     * @return
     * @author zhangs
     * @createDate 2019/6/26
     */
    @Test
    public void compatibleActiveDateToStr() {
        Integer activeDateType = 3;
        Date activeDate = null;
        try {
            activeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-06-26 10:30:25");
        } catch (Exception e) {
            System.out.println(e);
        }
        if (activeDateType == null || activeDate == null) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(activeDate);
        StringBuffer result = new StringBuffer();
        for (;activeDateType > 0;) {
            ActiveDateFormatEnum activeDateFormatEnum = ActiveDateFormatEnum.getEnum(activeDateType);
            if (activeDateFormatEnum == null) {
                break;
            }
            try {
                result.append(calendar.get(activeDateFormatEnum.getField()));
                if (activeDateType > 1) {
                    if (activeDateType > ActiveDateFormatEnum.ACTIVE_DATE_FORMAT_DAY.getTypeCode()) {
                        result.append("-");
                    } else if (ActiveDateFormatEnum.ACTIVE_DATE_FORMAT_DAY.getTypeCode().equals(activeDateType)) {
                        result.append(" ");
                    } else {
                        result.append(":");
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            --activeDateType;
        }

        System.out.println(result.toString());
        return;
    }
}
