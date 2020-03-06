package com.my.aspect.controller;

import com.my.aspect.annotion.MyLog;
import org.springframework.web.bind.annotation.*;

/**
 * @module:
 * @author:
 * @date: 2018 2018/12/25
 * @version: v1.0
 */
@RestController
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    @MyLog("这是一个自定义注解切面日志---")
    public String hello(@RequestParam String name) {
        System.out.println(name);
        return "Hello " + name;
    }

}
