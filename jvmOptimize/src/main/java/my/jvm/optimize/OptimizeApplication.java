package my.jvm.optimize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OptimizeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OptimizeApplication.class, args);
    }

}
