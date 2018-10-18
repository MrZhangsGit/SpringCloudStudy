/*************************************************************************
 *          HONGLING CAPITAL CONFIDENTIAL AND PROPRIETARY
 *
 *          COPYRIGHT (C) HONGLING CAPITAL CORPORATION 2012
 *    ALL RIGHTS RESERVED BY HONGLING CAPITAL CORPORATION. THIS PROGRAM
 * MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS FURNISHED BY
 * HONGLING CAPITAL CORPORATION. NO PART OF THIS PROGRAM MAY BE REPRODUCED
 * OR DISCLOSED TO OTHERS, IN ANY FORM, WITHOUT THE PRIOR WRITTEN
 * PERMISSION OF HONGLING CAPITAL CORPORATION. USE OF COPYRIGHT NOTICE
 * DOES NOT EVIDENCE PUBLICATION OF THE PROGRAM.                       
 *          HONGLING CAPITAL CONFIDENTIAL AND PROPRIETARY
 *************************************************************************/

package DateUtil;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间格式化工具类。
 *
 * @Wenchao.L
 * @version 1.0
 * @date 15-3-24 下午5:04
 */
public class DateUtil {


    public static final String DATE_FORMAT_TYPE1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_TYPE3 = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT_TYPE4 = "MM/dd/yyyy";
    public static final String DEFAULTF = "EEE MMM dd HH:mm:ss z yyyy";
    public static final String YMDE = "MM/dd/yyyy";
    public static final String YMDN = "yyyy/MM/dd";
    public static final String YMD = "MM月dd日yyyy年";
    public static final String YMDEC = "yyyy年MM月dd日";
    public static final String YMDSQL = "yyyy-MM-dd";
    public static final String HMSE = "HH:mm:ss";
    public static final String HMSC = "HH时mm分ss秒";
    public static final String DATE_FORMAT_TYPE2 = "yyyyMMddHHmmss";
    public static final String YYMMDD = "yy/MM/dd";
    public static final String YYMMDDHHMMSS = "yyMMddHHmmssSSS";
    public static final String YYYYMMDDHHMM="yyyy-MM-dd HH:mm";
    public static final String YYMMDDHHMM="yyMMddHHmm";

    /**
     * <desc>
     *      将时间戳转为日期
     * </desc>
     * @param longTime
     * @return
     * @throws ParseException
     * @author Jiaqi.X
     * @createDate 2017/11/10
     */
    public static Date formatLong(Long longTime) {
        SimpleDateFormat format =  new SimpleDateFormat(DATE_FORMAT_TYPE1);
        Long time=new Long(longTime.toString());
        String d=format.format(time);
        try {
            return format.parse(d);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * <desc>
     *      返回增加month月份后的时间
     * </desc>
     *
     * @param time 需要增加月份的时间
     * @param month 需要增加的月份数量
     * @return
     * @author Jiaqi.X
     * @createDate 2017/11/13
     */
    public static Date getAddMonth(Date time,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH,month);
        return calendar.getTime();
    }

    /**
     * <p>
     *     计算两个日期的时间差
     * </p>
     *
     * @param nowTime 当前时间
     * @param passTime 过去时间
     *
     */
    public static Map<String,Object> reckonDate(String nowTime,String passTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = null;
        Date date=null;
        try {
            now = df.parse(nowTime);
            date=df.parse(passTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Map<String,Object> map=new HashMap<String, Object>();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000));
        long min = ((l / (60 * 1000)));
        long s = (l / 1000);
        map.put("day", day);
        map.put("hour",hour);
        map.put("min",min);
        map.put("s",s);
        return map;
    }

    /**
     * 制定时间格式返回String格式的时间
     * @param calendar 时间
     * @param pattern 格式
     * @return
     */
    public static String format(Calendar calendar, String pattern) {
        FastDateFormat df = FastDateFormat.getInstance(pattern, null,null);
        return df.format(calendar);
    }

    /**
     * 当前时间进行累加
     * @param day 累加的天数
     * @return
     */
    public static Date addCurrentTime(Integer day){
        try {
        SimpleDateFormat sf = new SimpleDateFormat(YMDSQL);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, day);
        return sf.parse(sf.format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <desc>
     *      获取当前时间加上？小时后的时间
     * </desc>
     * @param now 当前时间
     * @param addTime 毫秒
     * @return
     */
    public static Date addCurrentTime(Date now,long addTime){
        long time=now.getTime();
        time+=addTime;
        return new Date(time);
    }



    /**
     * <desc>
     *      比较两个时间的大小 并返回
     * </desc>
     *
     * @param date1
     * @param date2
     * @return
     * @author Jiaqi.X
     * @createDate 2017/11/24
     */
    public static String compareSize(String date1,String date2) throws ParseException {
        if(StringUtils.isNotBlank(date1) && StringUtils.isNotBlank(date2)){
            Long d1=formatStringToDate(date1,YYMMDDHHMMSS).getTime();
            Long d2=formatStringToDate(date2,YYMMDDHHMMSS).getTime();
            if(d1>d2){
                return date1;
            }else{
                return date2;
            }
        }else if(StringUtils.isNotBlank(date1)){
            return date1;
        }else if(StringUtils.isNotBlank(date2)){
            return date2;
        }
        return "";
    }

    /**
     * 比较两个日期相差多少天
     * @param fDate
     * @param oDate
     * @return
     */
    public static Integer daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    /**
     * 计算两个时间相差多少秒。
     *
     * @param str1 时间。
     * @param str2 被减时间。
     *@return 相差秒。
     */
    public static long timeComparison(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long diff =0 ;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();

            diff = time1 - time2;
        } catch (ParseException e) {
            System.out.println(e);
        }
        return diff/1000;
    }

    /**
     * 将date时间转换String类型。
     *
     * @param date 。
     *@return 转换时间。
     */
    public static String fomatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_TYPE1);
        return sdf.format(date);
    }

    /**
     * 将date时间转换String类型重载方法。
     *
     * @param date 。
     *@return 转换时间。
     */
    public static String fomatDate(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将date时间转换String类型 "yyyy/MM/dd HH:mm:ss.SSS"。
     *
     * @param date 。
     *@return 转换时间。
     */
    public static String fomatDate1(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        return sdf.format(date);
    }


    /**
     * 将Long型的date时间转换Date型时间。
     *
     * @param date 。
     *@return 转换时间。
     */
    public static Date fomatLongDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_TYPE1);
        Date d = sdf.parse(date);
        return d;
    }

    public static Date fomatLongDate2(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YMDSQL);
        Date d = sdf.parse(date);
        return d;
    }



    /**
     * 将String型的"yyyy/MM/dd HH:mm:ss" 转换Date型时间。
     *
     * @param date 。
     *@return 转换时间。
     */
    public static Date formatStringToDate(String date,String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = sdf.parse(date.trim());
        return d;
    }



    /**
     * 将yyyy-MM-dd 转换为yyyy/MM/dd。
     * @param date 。
     *@return 转换时间。
     */
    public static String fomatStringDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YMDSQL);
        SimpleDateFormat sdf2 = new SimpleDateFormat(YMDN);
        return sdf2.format(sdf.parse(date));
    }

    /**
     * 将yyyy/MM/dd转换为yyyy-MM-dd 。
     * @param date 。
     *@return 转换时间。
     */
    public static String fomatStringDate2(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YMDN);
        SimpleDateFormat sdf2 = new SimpleDateFormat(YMDSQL);
        return sdf2.format(sdf.parse(date));
    }

    /**
     * 将日期转换为yyyy-MM-dd 。
     * @param date 。
     *@return 转换时间。
     */
    public static String fomatStringDate4(Date date) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String fomatString(String date){
        SimpleDateFormat sdf = new SimpleDateFormat(YMDN);
        return sdf.format(date);
    }


    /**
     * 获取格式为 ’yyyyMMddHHmmss‘当前时间戳。
     */
    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_TYPE2);
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 获取格式为 ’yyyyMMddHHmmss‘当前时间戳（十位数）。
     */
    public static String getCurrentTimeTen(){
        Long l = Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10));
        return l.toString();
    }

    /**
     * 获取今天起始时间（yyyy-MM-dd 00:00:00）
     */
    public static Calendar getTodayStartTime(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    /**
     * 计算两个时间之间相差的秒数
     */
    public static long calcSecondByTwoDays(Date date1, Date date2) {

        return Math.abs(date1.getTime() - date2.getTime()) / 1000;
    }

    /**
     * 计算某一个时间与当前时间相差的秒数
     */
    public static long calcSecondFromTodayToTheOtherDay(Date date) {

        return Math.abs(System.currentTimeMillis() - date.getTime()) / 1000;
    }

    /**
     * 获取今天剩余时间的秒数
     */
    public static long getTodaySurplusSeconds(){

        Calendar calendar = getTodayStartTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);

        return calcSecondFromTodayToTheOtherDay(calendar.getTime());
    }


    public static String getSQLDate(Date date)
    {
        return formatDate(date, "yyyy-MM-dd", "HH:mm:ss");
    }

    public static String getSQLDate()
    {
        return formatDate(new Date(), "yyyy-MM-dd", "HH:mm:ss");
    }

    public static SimpleDateFormat getBasicFormat(String format)
    {
        return new SimpleDateFormat(format);
    }

    public static Date getCurrentDate()
    {
        return new Date();
    }

    public static String formatDate(Date date, String dateType, String timeType)
    {
        String format = "";
        if (dateType != null) {
            format = dateType + " ";
        }
        if (timeType != null) {
            format = format + timeType;
        }
        return getBasicFormat(format).format(date);
    }

    public static Date parseString(String format, String date)
    {
        try
        {
            return getBasicFormat(format).parse(date);
        }
        catch (ParseException e)
        {
        }
        return null;
    }

    public static String parseDateLong(long value)
    {
        Date dt = new Date(value);
        return formatDate(dt, "yyyy/MM/dd", "HH:mm:ss");
    }

    public static Date parseSQLString(String date)
    {
        String ydate = "";
        String ytime = "";
        String dType = "yyyy年MM月dd日";
        String tType = "HH:mm:ss";
        if (3 < date.split(" ").length) {
            try
            {
                return new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US).parse(date);
            }
            catch (ParseException e)
            {
                return null;
            }
        }
        if (date != null)
        {
            if (date.indexOf(" ") > 0) {
                ydate = date.substring(0, date.indexOf(" "));
            } else {
                ydate = date;
            }
            if (10 < date.indexOf(" ") + 1) {
                ytime =
                        date.substring(date.indexOf(" ") + 1, date.length());
            } else {
                ytime = "00:00:00";
            }
        }
        if (ydate != null) {
            if (ydate.indexOf("-") > 0) {
                dType = "yyyy-MM-dd";
            } else if (ydate.indexOf("年") > 0)
            {
                if ((ydate.indexOf("年") > 0) && (ydate.indexOf("月") > 0) &&
                        (ydate.indexOf("年") < ydate.indexOf("月"))) {
                    dType = "yyyy年MM月dd日";
                } else if ((ydate.indexOf("年") > 0) &&
                        (ydate.indexOf("月") > 0) &&
                        (ydate.indexOf("年") > ydate.indexOf("月"))) {
                    dType = "MM月dd日yyyy年";
                }
            }
            else if ((ydate.indexOf("/") > 0) && (ydate.indexOf("/") == 4)) {
                dType = "yyyy/MM/dd";
            } else if ((ydate.indexOf("/") > 0) &&
                    (ydate.indexOf("/") == 2)) {
                dType = "MM/dd/yyyy";
            }
        }
        if (1 < ytime.indexOf("时"))
        {
            tType = "HH时mm分ss秒";
            if (ytime.length() == 6) {
                ytime = ytime + "00秒 ";
            }
        }
        else if (1 < ytime.indexOf(":"))
        {
            tType = "HH:mm:ss";
            if (ytime.length() == 5) {
                ytime = ytime + ":00";
            }
        }
        else
        {
            tType = null;
        }
        date = ydate + " " + ytime;
        return parseString(dType + " " + tType, date);
    }

    public static int dayOfWeek()
    {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(7);
        return dayOfWeek;
    }

    public static int dayOfWeek(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(7);
        return dayOfWeek;
    }

    public static Boolean isSunday()
    {
        if (dayOfWeek() == 1) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isSunday(Date date)
    {
        if (dayOfWeek(date) == 1) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isMonday()
    {
        if (dayOfWeek() == 2) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isMonday(Date date)
    {
        if (dayOfWeek(date) == 2) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isTuesday()
    {
        if (dayOfWeek() == 3) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isTuesday(Date date)
    {
        if (dayOfWeek(date) == 3) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isWednesday()
    {
        if (dayOfWeek() == 4) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isWednesday(Date date)
    {
        if (dayOfWeek(date) == 4) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isThursDay()
    {
        if (dayOfWeek() == 5) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isThursDay(Date date)
    {
        if (dayOfWeek(date) == 5) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isFriday()
    {
        if (dayOfWeek() == 6) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isFriday(Date date)
    {
        if (dayOfWeek(date) == 6) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isSaturday()
    {
        if (dayOfWeek() == 7) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isSaturday(Date date)
    {
        if (dayOfWeek(date) == 7) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isWeekEnd()
    {
        if ((dayOfWeek() == 1) || (dayOfWeek() == 7)) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Boolean isWeekEnd(Date date)
    {
        if ((dayOfWeek(date) == 1) || (dayOfWeek(date) == 7)) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public static Calendar getCalendar(Date date)
    {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String addYears(String ymd, int years)
    {
        Calendar calendar = null;
        calendar = getCalendar(parseSQLString(ymd));
        calendar.add(1, years);
        return getSQLDate(calendar.getTime());
    }

    public static String addMonths(String ymd, int months)
    {
        Calendar calendar = null;
        calendar = getCalendar(parseSQLString(ymd));
        calendar.add(2, months);
        return getSQLDate(calendar.getTime());
    }

    public static String addDays(String ymd, int days)
    {
        Calendar calendar = null;
        calendar = getCalendar(parseSQLString(ymd));
        calendar.add(5, days);
        return getSQLDate(calendar.getTime());
    }

    public static String addHours(String ymd, int hours)
    {
        Calendar calendar = null;
        calendar = getCalendar(parseSQLString(ymd));
        calendar.add(11, hours);
        return getSQLDate(calendar.getTime());
    }

    public static String addMinuts(String ymd, int minutes)
    {
        Calendar calendar = null;
        calendar = getCalendar(parseSQLString(ymd));
        calendar.add(12, minutes);
        return getSQLDate(calendar.getTime());
    }

    public static String addSecond(String dateTime, int seconds)
    {
        Calendar calendar = null;
        calendar = getCalendar(parseSQLString(dateTime));
        calendar.add(13, seconds);
        dateTime = getSQLDate(calendar.getTime());
        return dateTime;
    }

    public static String addMilliSecond(String dateTime, int microSeconds)
    {
        Calendar calendar = null;
        calendar = getCalendar(parseSQLString(dateTime));
        calendar.add(14, microSeconds);
        dateTime = getSQLDate(calendar.getTime());
        return dateTime;
    }

    public static long getDistanceMilliSecondsString(String str1, String str2)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one = getCurrentDate();
        Date two = one;
        try
        {
            one = df.parse(str1);

            two = df.parse(str2);
        }
        catch (ParseException e)
        {
        }
        return getDistanceMilliSecondsDate(one, two);
    }

    public static boolean getDistanceMilliSecondsMaxflag(Date one, Date two)
    {
        long time1 = one.getTime();
        long time2 = two.getTime();
        if (time1 < time2) {
            return false;
        }
        return true;
    }

    public static long getDistanceMilliSecondsDate(Date one, Date two)
    {
        long milliSeconds = 0L;
        long time1 = one.getTime();
        long time2 = two.getTime();
        if (time1 < time2) {
            milliSeconds = time2 - time1;
        } else {
            milliSeconds = time1 - time2;
        }
        return milliSeconds;
    }

    /**
     * <p>
     *     获取跟现在相差days天的日期
     * </p>
     * @return
     */
    public static Date getAFewDaysFromNow(int days){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    /**
     * <p>
     *     获取跟某个日期相差days天的日期
     * </p>
     * @return
     */
    public static Date getAFewDaysFromOneDate(Date date, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    /**
     * <p>
     *     获取跟现在相差days天的日期并格式化
     * </p>
     * @return
     */
    public static String getAFewDaysFromNowAndFormat(int days, String dateFormat){
        Date date = getAFewDaysFromNow(days);
        return new SimpleDateFormat(dateFormat).format(date);
    }

    /**
     * <p>
     *     获取距离当前N分钟的时间字符串
     * </p>
     * @param minutes
     * @param pattern
     * @return
     */
    public static String getTimeBeforeFiveMinutesFromNow(int minutes, String pattern){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minutes);
        return new SimpleDateFormat(pattern).format(cal.getTime());
    }

    public static long getDistanceSecondsString(String str1, String str2)
    {
        return getDistanceMilliSecondsString(str1, str2) / 1000L;
    }

    public static long getDistanceSecondsDate(Date one, Date two)
    {
        return getDistanceMilliSecondsDate(one, two) / 1000L;
    }

    public static long getDistanceMinutesString(String str1, String str2)
    {
        return getDistanceMilliSecondsString(str1, str2) / 60000L;
    }

    public static long getDistanceMinutesDate(Date one, Date two)
    {
        return getDistanceMilliSecondsDate(one, two) / 60000L;
    }

    public static long getDistanceHoursString(String str1, String str2)
    {
        return getDistanceMilliSecondsString(str1, str2) / 3600000L;
    }

    public static long getDistanceHoursDate(Date one, Date two)
    {
        return getDistanceMilliSecondsDate(one, two) / 3600000L;
    }

    public static long getDistanceDaysString(String str1, String str2)
    {
        return getDistanceMilliSecondsString(str1, str2) / 86400000L;
    }

    public static long getDistanceDaysDate(Date one, Date two)
    {
        return getDistanceMilliSecondsDate(one, two) / 86400000L;
    }
}
