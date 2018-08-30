package com.oeasy.myfeignserver.server;

import com.oeasy.myfeignserver.service.SchedualServiceClientService;
import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHelloHystric implements SchedualServiceClientService {
    @Override
    public String sayHelloFromClient(String name) {
        return "sorry, " + name;
    }
}
