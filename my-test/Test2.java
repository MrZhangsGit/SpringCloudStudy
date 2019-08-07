import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.ast.Str;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/6/6
 */
public class Test2 {
    @Test
    public void testDateStr() {

        SimpleDateFormat sdf;

        String string1 = "50";
        sdf = new SimpleDateFormat("ss");
        try {
            Date date = sdf.parse(string1);
            System.out.println(date);
        } catch (Exception e) {
            System.out.println(e);
        }

        String string2 = "10:50";
        sdf = new SimpleDateFormat("mm:ss");
        try {
            Date date = sdf.parse(string2);
            System.out.println(date);
        } catch (Exception e) {
            System.out.println(e);
        }

        String string3 = "9:10:10";
        sdf = new SimpleDateFormat("HH");
        try {
            Date date = sdf.parse(string3);
            System.out.println(date);

        } catch (Exception e) {
            System.out.println(e);
        }

        String string4 = "25 10:10:00";
        sdf = new SimpleDateFormat("dd HH:mm:ss");
        try {
            Date date = sdf.parse(string4);
            System.out.println(date);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testCalendar() {
        Date date2 = new Date();
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            System.out.println(e);
        }

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        System.out.println(date2.compareTo(date));
        date2 = date;
        System.out.println(date2.compareTo(date));
    }

    @Test
    public void testCalendar2() {
        Date date = new Date();
        System.out.println(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println(calendar.getTime());
    }

    @Test
    public void testAddList() {
        List<String> result = new ArrayList<>();
        List<String> strList = new ArrayList<>();
        List<String> strList2 = new ArrayList<>();
        strList.add("123");
        strList.add("abc");
        strList2.add("456");
        strList2.add("def");
        strList2.add("ghi");
        result.addAll(strList);
        result.addAll(strList2);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void getWeekDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println(calendar.get(Calendar.MINUTE));
        System.out.println(calendar.get(Calendar.SECOND));
    }

    @Test
    public void getDayOfWeek() {
        LocalDateTime ldt = LocalDateTime.now();
        int weekDay = ldt.getDayOfWeek().getValue();
        System.out.println(weekDay);

        long nowTime = ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long nowTime2 = System.currentTimeMillis();
        System.out.println("Time1:" + nowTime + ", Time2:" + nowTime2);
    }

    @Test
    public void testReplace() {
        String content = "0101010";
        content = content.replaceAll("1", "");
        content = content.replaceAll("0", "");
        System.out.println(StringUtils.isEmpty(content));
        System.out.println(content);
    }

    @Test
    public void testMapPut() {
        Map<String, Object> map = new HashMap<>();
        map.put(null, "1");
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void testSort() {
        List<Integer> ints = new ArrayList<Integer>();
        ints.add(3);
        ints.add(1);
        ints.add(5);
        ints.add(2);
        ints.add(4);
        System.out.println(JSON.toJSONString(ints));
        /*//顺序
        Collections.sort(ints);
        System.out.println(JSON.toJSONString(ints));
        //倒序
        Collections.sort(ints, Collections.reverseOrder());
        System.out.println(JSON.toJSONString(ints));*/
        Collections.sort(ints, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 < 3 ? -1:1;
            }
        });
        System.out.println(JSON.toJSONString(ints));
    }

    @Test
    public void testTimeCron() {
        String cron = "1 19 17 ? * MON,FRI,SAT,SUN";
        Map cronMap = quartzTranslateToTime(cron);
        System.out.println(JSON.toJSONString(cronMap));
        List<String> weekList = (List<String>) cronMap.get("week");
        System.out.println(JSON.toJSONString(weekList));
    }

    public Map quartzTranslateToTime(String cron) {
        if (StringUtils.isEmpty(cron)) {
            return null;
        }
        Map<String, Object> timeCronMap = new HashMap<>();

        String secondStr = cron.substring(0, cron.indexOf(" "));
        timeCronMap.put("second", getNumber(secondStr));
        cron = cron.substring(cron.indexOf(" ") + 1);

        String minuteStr = cron.substring(0, cron.indexOf(" "));
        timeCronMap.put("minute", getNumber(minuteStr));
        cron = cron.substring(cron.indexOf(" ") + 1);

        String hourStr = cron.substring(0, cron.indexOf(" "));
        timeCronMap.put("hour", getNumber(hourStr));
        cron = cron.substring(cron.indexOf(" ") + 1);

        String dayStr = cron.substring(0, cron.indexOf(" "));
        cron = cron.substring(cron.indexOf(" ") + 1);

        String monthStr = cron.substring(0, cron.indexOf(" "));
        cron = cron.substring(cron.indexOf(" ") + 1);

        String weekStr = cron;
        timeCronMap.put("week", getWeekList(weekStr));

        return timeCronMap;
    }

    private List<String> getWeekList(String content) {
        if (content.length() <= 2) {
            return null;
        }
        String[] weeks = content.split(",");
        if (weeks == null || weeks.length <= 0) {
            return null;
        }
        List<String> weekList = new ArrayList<>();
        for (String week:weeks) {
            weekList.add(week);
        }
        return weekList;
    }

    private Integer getNumber(String content) {
        if (StringUtils.isNumeric(content)) {
            return Integer.parseInt(content);
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i=0;i<content.length();i++) {
                String index = content.substring(i, i + 1);
                if (StringUtils.isNumeric(index)) {
                    stringBuffer.append(index);
                } else {
                    i = content.length();
                }
            }
            if (StringUtils.isNotEmpty(stringBuffer.toString()) && StringUtils.isNumeric(stringBuffer.toString())) {
                return Integer.parseInt(stringBuffer.toString());
            } else {
                return 0;
            }
        }
    }

    @Test
    public void testDivisionAndRemainder() {
        Integer delay = 70;
        Integer minute = delay/60;
        Integer second = delay%60;
        //System.out.println("minute:" + minute + ", second:" + second);

        Date date = new Date();
        System.out.println(date);
        long dateTime = date.getTime();
        System.out.println(dateTime);
        dateTime += delay*1000;
        System.out.println(dateTime);
        date = new Date(dateTime);
        System.out.println(date);
        System.out.println(date.getTime());
    }

    @Test
    public void testDevAlert() {
        DevAlertPushTemplateDTO devAlertPushTemplateDTO = new DevAlertPushTemplateDTO();
        devAlertPushTemplateDTO.setDeviceName("devName");
        devAlertPushTemplateDTO.setTemplateKey("123456");
        System.out.println(JSON.toJSONString(devAlertPushTemplateDTO));
    }

    @Test
    public void testBuffer() {
        StringBuffer stringBuffer = new StringBuffer();
        /*System.out.println(StringUtils.isNoneEmpty(stringBuffer));
        stringBuffer.append("123");
        System.out.println(StringUtils.isNoneEmpty(stringBuffer));
        stringBuffer.append(",");
        String outputIds = stringBuffer.toString();
        System.out.println(outputIds);
        if (outputIds.endsWith(",")) {
            outputIds = outputIds.substring(0, outputIds.length() - 1);
        }
        System.out.println(outputIds);*/
        stringBuffer.append("123").append(",").append("abc").append(",").append(456);
        List<String> list = Arrays.asList(stringBuffer.toString().split(","));
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testMap() {
        Map<String, String> map = new HashMap<>();
        map.put("123", "abc");
        map.put("456", "def");
        map.put("789", "ghi");
        String str = JSON.toJSONString(map);
        System.out.println(str);
        Map<String, String> map2 = JSON.parseObject(str, Map.class);
        System.out.println(map2.keySet());
    }
}
