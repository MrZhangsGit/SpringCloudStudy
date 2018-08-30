package com.schedule.my.schedule.schedule.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 转换工具
 */
public class TransformationUtils {

    /**
     * cron表达式支持7域,即支持年份
     */
    static final String Quartz_date_fromat = "ss mm HH dd MM ? yyyy";

    /**
     * cron表达式支持6域,即不支持年份
     */
    static final String TASK_DATE_FORMAT = "ss mm HH dd MM ?";

    /**
     * 日期转换成cron表达式
     * @param date
     * @param dateFormat e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = format.format(date);
        }
        return formatTimeStr;
    }

    /*public static void main(String[] args) {
//        System.out.println(formatDateByPattern(new Date(), DATE_FORMAT));
//        System.out.println(new Date("2018/7/6/ 9:47:06"));
    }*/
}
