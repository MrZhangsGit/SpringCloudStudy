package com.oeasycloud.myconfigclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyConfigClientController {

    @Value("${key}")
    String key;

    @RequestMapping(value = "getKey")
    public String getKey() {
        return key;
    }
}
