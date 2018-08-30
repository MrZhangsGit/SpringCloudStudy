package com.oeasy.myfeignserver.web;

import com.oeasy.myfeignserver.service.SchedualUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserHelloController {
    @Autowired
    SchedualUserService schedualUserService;

    @RequestMapping(value = "/user/hello", method = RequestMethod.GET)
    public String sayHello(@RequestParam String name){
        return schedualUserService.sayHelloFromUserServer(name);
    }
}
