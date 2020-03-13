package com.oeasycloud.myeurekaclient.controller;

import com.oeasycloud.myeurekaclient.config.ThreadPoolRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

@RestController
public class ClientController {
    @Autowired
    private ThreadPoolRunner threadPoolRunner;

    @Value("${server.port}")
    String port;
    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        ThreadPoolExecutor threadPoolExecutor = threadPoolRunner.getThreadPoolExecutor();
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("");
            }
        });

        return "hello "+name+",i am from port:" +port;
    }
}
