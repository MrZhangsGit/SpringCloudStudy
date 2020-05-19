package my.dataway.demo;

import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableHasor()
@EnableHasorWeb()
@SpringBootApplication(scanBasePackages = {"my.dataway.demo"})
public class DatawayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatawayApplication.class, args);
    }

}
