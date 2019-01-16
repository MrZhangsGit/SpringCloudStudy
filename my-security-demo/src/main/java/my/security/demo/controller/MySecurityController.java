package my.security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/14
 */
@RestController
@EnableWebSecurity
public class MySecurityController {
    @RequestMapping(value = "/security")
    public String saySecurity() {
        return "Hello Security!";
    }

    @RequestMapping(value = "/hello")
    public String sayHello() {
        return "Hello !";
    }

    @RequestMapping(value = "/")
    public String say() {
        return "Hello / !";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/authorize")
    public String authorize() {
        return "有访问权限!";
    }
}
