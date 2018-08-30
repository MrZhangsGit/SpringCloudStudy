package com.my.filter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyFilterController {
    @RequestMapping("/")
    public String sayHello() {
        return "Hello !";
    }
}
