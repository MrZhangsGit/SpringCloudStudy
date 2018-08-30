package com.oeasycloud.myuserserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserServerController {

    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        return "User:" + name + ",Welcome!";
    }
}
