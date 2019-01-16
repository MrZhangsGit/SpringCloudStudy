package com.my.filter.controller;

import com.my.filter.po.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyFilterController {
    @RequestMapping("/")
    public String sayHello() {
        return "Hello !";
    }

    @RequestMapping(value = "/testAPI", method = RequestMethod.POST)
    public String testAPI(@RequestParam("content") String content) {
        return content;
    }

    @RequestMapping(value = "/testAPI2", method = RequestMethod.POST)
    public String testAPI2(@RequestBody User user) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("User  name:" + user.getName() + ",age:" + user.getAge());
        return stringBuffer.toString();
    }
}
