package com.cloud.emqtt.controller;

import com.cloud.emqtt.EmqttApplication;
import com.cloud.emqtt.config.Person;
import com.cloud.emqtt.service.MqttManager;
import com.cloud.emqtt.service.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @module: com.cloud.emqtt.controller
 * @author: chenfei
 * @date: 2018 2018/12/20
 * @version: v1.0
 */
@Controller
public class EmqController {
    @Autowired
    private MqttManager mqttManager;

    @Autowired
    private ThreadPool threadPool;

    private Logger logger = LoggerFactory.getLogger(EmqttApplication.class);

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String welcome() {
        for (int i = 0; i < 10; ++i) {
            threadPool.commint(new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取开始时间
                        long startTime = System.currentTimeMillis();
                        //获取结束时间
                        long endTime = System.currentTimeMillis();
                        long nCount = 0;
                        //获取结束时间
                        while (endTime - startTime < 10000) {
                            mqttManager.grab2pub()
                                    .publish("aiot/ww", "0134567890sbfkjshfjksd09000000000".getBytes(), 2, false);
                            ++nCount;
                            Thread.sleep(1);
                            endTime = System.currentTimeMillis();
                        }
                        Thread.sleep(10 * 1000);
                        //输出程序运行时
                        logger.info("|||||| QPS: nCount:" + nCount + ",time:" + (endTime - startTime) + "ms");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return "welcome to here!!!";
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public boolean subscribe(@RequestParam(value = "topic") String topic, @RequestParam(value = "qos") int qos) {
        logger.info("subscribe topic[" + topic + "],qos[" + qos + "] ok.");
        return mqttManager.grab2Sub().subscribe(topic, qos);
    }

    @RequestMapping(value = "/search/{topic}", method = RequestMethod.GET)
    public boolean subscribe(@PathVariable(value = "topic") String topic) {
        logger.info("subscribe topic[" + topic + "] ok.");
        return true;
        //mqttManager.grab2Sub().subscribe(topic, qos);
    }

    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public boolean header(@RequestHeader(value = "topic") String topic) {
        logger.info("subscribe topic[" + topic + "] ok.");
        return true;
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(@RequestParam(value = "name") String name) {
        logger.info("query topic[" + name + "] ok.");
        Person person = new Person();
        person.setName(name);
        person.setAddress("shenzhen");
        person.setCity("guangzhou");
        logger.info(person.toString());
        return person;
    }

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    @ResponseBody
    public Object first() {
        logger.info("first controller");
        return "first controller";
    }

    @RequestMapping(value = "/doError", method = RequestMethod.GET)
    @ResponseBody
    public Object error() {
        logger.info("exception");
        return 1 / 0;
    }

    @RequestMapping(value = "/hello/{cid}", method = RequestMethod.GET)
    @ResponseBody
    public Object getList(@PathVariable Long cid,
                          @RequestParam(value = "name") String name,
                          @RequestParam(value = "sex", defaultValue = "5") String sex,
                          @RequestParam(value = "age", defaultValue = "1") Integer age) {
        return "welcome id[" + cid + "], name:" + name + ", sex:" + sex + ", age:" + age;
    }
}
