package com.my.mqtt.producer.controller;

import com.my.mqtt.producer.service.IEmqService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MQTTServerController {
    @Autowired
    private IEmqService iEmqService;

    String TOPIC = "MQTT_PRODUCER_TOPIC";

    @RequestMapping("/")
    public String sayHello() {
        return "Hello !";
    }

    @GetMapping("/send/msg")
    public boolean send(@RequestParam String msg) throws MqttException {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "==========:" + msg);
        return iEmqService.publish(TOPIC, msg);
    }
}
