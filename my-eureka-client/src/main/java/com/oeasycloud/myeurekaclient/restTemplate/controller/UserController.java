package com.oeasycloud.myeurekaclient.restTemplate.controller;

import com.oeasycloud.myeurekaclient.restTemplate.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangs
 * @Description my_restTemplate
 * @createDate 2019/1/16
 */
@RestController
@Slf4j
public class UserController {

    /**
     * 无参get请求
     * @return
     */
    @RequestMapping(value = "/getAll")
    public List<UserEntity> getUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("1");
        userEntity.setUserName("admin");
        userEntity.setPassword("123456");
        userEntity.setAge(25);
        userEntity.setEmail("123456@qq.com");

        List<UserEntity> list = new ArrayList<>();
        list.add(userEntity);
        return list;
    }

    /**
     * 有参get
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}")
    public UserEntity getById(@PathVariable(name = "id") String id) {
        if (!"1".equals(id)) {
            return new UserEntity();
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId("1");
        userEntity.setUserName("admin");
        userEntity.setPassword("123456");
        userEntity.setAge(25);
        userEntity.setEmail("123456@qq.com");
        return userEntity;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private UserEntity create(@RequestBody UserEntity userEntity) {
        userEntity.setAge(26);
        userEntity.setEmail("123456@qq.com");
        return userEntity;
    }
}
