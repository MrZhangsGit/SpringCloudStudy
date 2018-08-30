package com.oeasy.myfeignserver.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "my-user-server")
public interface SchedualUserService {
    @RequestMapping(value = "/user/hi", method = RequestMethod.GET)
    String sayHelloFromUserServer(@RequestParam(value = "name") String name);
}
