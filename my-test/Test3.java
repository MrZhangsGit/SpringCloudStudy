import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.ast.Str;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/6/6
 */
public class Test3 {
    @Test
    public void testmap() {
        Map<String, Object> map = new HashMap<>();
        map.put("123", "abc");
        System.out.println("abc".equals(map.get("123")));
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    @Test
    public boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    @Test
    public void testJSONFiled() {
        User user = new User();
        user.setStartDate(new Date());
        String dateStr = JSON.toJSONString(user);
        System.out.println(dateStr);
        String string = "{\"date\":1566889954989, \"age\":27, \"name\":\"zs\"}";
        System.out.println(string);
        user = JSON.parseObject(string, User.class);
        System.out.println(JSON.toJSONString(user));
    }

    @Test
    public void testSet() {
        List<String> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            Set<String> set = new HashSet<>(list);
            System.out.println(JSON.toJSONString(set));
        }
        System.out.println("end");
    }

    @Test
    public void testPattern() {
        System.out.println(!Pattern.matches("^[" + 2 + "]|[" + 3 + "]$",
                String.valueOf(2)));
    }

    @Test
    /**
     * 替换反斜杠
     */
    public void testReplace() {
        String json = "{\"data\":\"{\\\"deviceId\\\":\\\"16efcb58b08a4869df9ca42c637840ac\\\",\\\"list\\\":{\\\"mDeviceCMDCode\\\":\\\"1\\\"},\\\"timestamp\\\":1571195703013}\",\"deviceId\":\"16efcb58b08a4869df9ca42c637840ac\",\"msgType\":\"reportAttribute\",\"serial\":0,\"timestamp\":1571195703013}";

        json = json.replaceAll("\\\\","");

        System.out.println(json);
    }

    @Test
    public void testJSON1() {
        YardDataBO yardDataBO = new YardDataBO();
        yardDataBO.setIoType(0);
        yardDataBO.setPlateNumCN("京B172190");
        yardDataBO.setTelephone("13517456544");
        yardDataBO.setUnitId("971031");

        YardBasicBO yardBasicBO = new YardBasicBO();
        yardBasicBO.setData(JSON.toJSONString(yardDataBO));
        yardBasicBO.setTimestamp(1570613410L);
        System.out.println(JSON.toJSONString(yardBasicBO));
    }

    @Test
    public void testCollectionRemove() {
        List<String> list1 = new ArrayList<>();
        list1.add("123");
        list1.add("123");
        list1.add("456");
        list1.add("789");
        List<String> list12= new ArrayList<>();
        list12.add("123");
        list12.add("abc");
        System.out.println("list1:" + JSON.toJSONString(list1));
        System.out.println("list2:" + JSON.toJSONString(list12));
        list1.removeAll(list12);
        System.out.println("list1:" + JSON.toJSONString(list1));

        Map<String, String> map1 = new HashMap<>();
        map1.put("1", "1");
        map1.put("a", "a");
        map1.put("2", "2");
        Map<String, String> map2 = new HashMap<>();
        map2.put("a", "a");
        map2.put("b", "b");
        map2.put("3", "3");
        System.out.println("map1:" + JSON.toJSONString(map1));
        System.out.println("map2:" + JSON.toJSONString(map2));
        map1.putAll(map2);
        System.out.println("map1:" + JSON.toJSONString(map1));
    }
}

class User {
    private Date startDate;

    private String name;

    public Date getStartDate() {
        return startDate;
    }

    @JSONField(name = "date")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

@Data
class YardBasicBO {

    /**
     * 数据内容
     */
    private String data;

    /**
     * 时间戳
     */
    private Long timestamp;
}

@Data
class YardDataBO {

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 小区Id
     */
    private String unitId;

    /**
     * 完整车牌号
     */
    private String plateNumCN;

    /**
     * 进出类型(0-进|1-出)
     */
    private Integer ioType;
}

