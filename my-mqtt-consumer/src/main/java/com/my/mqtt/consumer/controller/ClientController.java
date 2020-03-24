package com.my.mqtt.consumer.controller;

import com.my.mqtt.consumer.service.IMqttPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ClientController {
    @Autowired
    private IMqttPublishService iMqttPublishService;


    @RequestMapping("/hi")
    public String home(@RequestParam String content) {
        iMqttPublishService.publish("MQTT_PRODUCER_TOPIC", content);

        return "hello "+ content;
    }
}
