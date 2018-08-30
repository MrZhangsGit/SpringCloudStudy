package com.oeasy.myfeignserver.web;

import com.oeasy.myfeignserver.service.SchedualServiceClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientHelloController {
    @Autowired
    SchedualServiceClientService schedualServiceClientService;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String sayHello(@RequestParam String name) {
        return schedualServiceClientService.sayHelloFromClient(name);
    }
}
