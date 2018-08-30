package com.oeasy.myfeignserver.service;

import com.oeasy.myfeignserver.server.SchedualServiceHelloHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "my-eureka-client", fallback = SchedualServiceHelloHystric.class)
public interface SchedualServiceClientService {
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    String sayHelloFromClient(@RequestParam(value = "name") String name);
}
