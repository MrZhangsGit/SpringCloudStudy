package my.mqtt.consumer2.service;

import java.util.ArrayList;

/**
 * @module: com.berl.emqtt.service
 * @author: chenfei
 * @date: 2018 2018/12/11
 * @version: v1.0
 */
public class SubscribleTopic {
    public final static ArrayList<String> subscrible = new ArrayList<>();
    static {
        subscrible.add("aiot/#");
    }
}
