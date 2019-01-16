package my.mqtt.consumer2;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import my.mqtt.consumer2.service.MqttManager;
import my.mqtt.consumer2.service.ThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Configuration
@EnableConfigurationProperties
@Slf4j
@RequestMapping("/test")
public class MyMqttConsumer2Application {

    public static void main(String[] args) {
        SpringApplication.run(MyMqttConsumer2Application.class, args);
    }

    @Autowired
    private MqttManager mqttManager;

    @Autowired
    private ThreadPool threadPool;

    @RequestMapping("/hello")
    public String welcome() {
        for (int i = 0;i < 10;i++) {
            threadPool.commint(new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取开始时间
                        long startTime = System.currentTimeMillis();
                        //获取结束时间
                        long endTime = System.currentTimeMillis();
                        long nCount = 0;
                        while (endTime - startTime < 10000) {
                            mqttManager.grab2pub().publish("aiot/ww", "0134567890sbfkjshfjksd09000000000".getBytes(), 2, false);
                            ++nCount;
                            Thread.sleep(1);
                            //获取结束时间
                            endTime = System.currentTimeMillis();
                        }
                        Thread.sleep(10*1000);
                        //输出程序运行时
                        log.info("--- QPS: nCount:" + nCount + ",time:" + (endTime - startTime) + "ms");
                    } catch (Exception e) {
                        log.error(JSON.toJSONString(e));
                    }
                }
            });
        }

        return "Hello!";
    }
}

