import SqlUtil.SqlUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author zhangs
 * @Description
 * @createDate 2018/11/21
 */
public class Test {
    public static void main(String[] args) {
        /*String project = UUID.randomUUID().toString().replace("-", "");
        System.out.println(project);
        System.out.println(project.substring(0, 10));*/

        /*List<Device> emqDataMOS = new ArrayList<>();
        Device emqDataMO = new Device();
        emqDataMO.setDeviceId("123456");
        emqDataMOS.add(emqDataMO);

        emqDataMO = new Device();
        emqDataMO.setDeviceId("987654");
        emqDataMOS.add(emqDataMO);

        emqDataMO = new Device();
        emqDataMO.setDeviceId("abcdef");
        emqDataMOS.add(emqDataMO);
        System.out.println(JSON.toJSONString(emqDataMOS));
        for (int i=0;i<emqDataMOS.size();i++) {
            emqDataMOS.get(i).setDeviceId("00000" + i);
        }
        System.out.println(JSON.toJSONString(emqDataMOS));*/

        /*List<String> deviceIds = new ArrayList<>();
        deviceIds.add("123456");
        deviceIds.add("789");
        deviceIds.add("abc");
        System.out.println(JSON.toJSONString(deviceIds));*/
        /*String projectKey = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        System.out.println(projectKey.length());*/

        /*System.out.println(UUID.randomUUID().toString().replace("-", ""));*/
        /*String user = "' or 1 = 1 -- ";
        System.out.println(":::" + user.replaceAll(".*([';]+|(--)+).*", " "));*/
        /*String content = "{1}";
        System.out.println(content.replaceAll("\\{", ""));
        System.out.println(content.replaceAll("\\}", ""));*/
        /*String content = "{}";
        System.out.println(SqlUtil.preventSQLInjection(content));*/
        /*Map<String, Object> groupMap = new HashMap<>();
        System.out.println(CollectionUtils.isEmpty(groupMap));*/
    }
}

class Device {
    private String deviceId;

    public Device() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
