package my.spring.cloud.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/22
 */
@Configuration
public class SampleTask {
    /*@Bean
    public CommandLineRunner commandLineRunner(){
        return new HelloWorldCommandLineRunner();
    }

    public static class HelloWorldCommandLineRunner implements CommandLineRunner {
        @Override
        public void run(String ... strings) throws Exception {
            System.out.println("Hello, World!");
        }
    }*/

}
