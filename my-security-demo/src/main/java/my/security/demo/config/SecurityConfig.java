package my.security.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/14
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 定义认证用户信息获取来源，密码校验规则等
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //从内测中获取
        auth.inMemoryAuthentication().withUser("123456").password("123456").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");

        //从数据库中获取
        /**
         * auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(query).authoritiesByUsernameQuery(query);
         * jdbcAuthentication从数据库中获取，但是默认是以security提供的表结构
         * usersByUsernameQuery 指定查询用户SQL
         * authoritiesByUsernameQuery 指定查询权限SQL
         */

        //注入userDetailsService，需要实现userDetailsService接口
        //auth.userDetailsService(userDetailsService);
    }

    /**
     * 安全策略
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()    //配置安全策略
                .antMatchers("/", "/hello").permitAll()     //定义/请求不需要验证
                .anyRequest().authenticated()   //其余所有请求都需要验证
                .and()
                .logout()   //logout不需要验证
                .permitAll()
                .and()
                .formLogin();   //使用表单登录

        /**
         * 退出地址在LogoutConfigurer类中，当然也是可以自定义的
         */
        httpSecurity.csrf().disable();  //security默认开启了csrf，禁用csrf
    }
}
