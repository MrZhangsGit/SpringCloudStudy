package my.jvm.optimize.controller;

import my.jvm.optimize.server.CyclicDependences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    @Autowired
    private CyclicDependences cyclicDependences;

    @RequestMapping("/hi")
    public String home() {
        cyclicDependences.run();

        return "hello ";
    }
}
