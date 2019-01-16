package com.oeasycloud.myeurekaclient.restTemplate.controller;

import com.alibaba.fastjson.JSON;
import com.oeasycloud.myeurekaclient.restTemplate.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * @author zhangs
 * @Description restTemplateDemo
 * @createDate 2019/1/16
 */
@RestController
@Slf4j
public class RestTemplateController {

    RestTemplate restTemplate = new RestTemplate();

    private static String URL = "http://192.168.2.190:8762";

    /**
     * 无参的get请求
     */
    @RequestMapping(value = "/rest/getAll")
    public List<UserEntity> getAll() {
        List<UserEntity> list = restTemplate.getForObject(URL +"/getAll", List.class);
        return list;
    }

    /**
     * 无参的get请求
     */
    @RequestMapping(value = "/rest/getForEntity")
    public List<UserEntity> getUserEntity() {
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(URL + "/getAll", List.class);
        HttpHeaders headers = responseEntity.getHeaders();
        HttpStatus status = responseEntity.getStatusCode();
        int statusCode = status.value();

        List<UserEntity> list = responseEntity.getBody();
        log.info("---list::{}", JSON.toJSONString(list));
        return list;
    }

    /**
     * 有参get
     */
    @RequestMapping(value = "/rest/get/{id}")
    public UserEntity getById(@PathVariable(name = "id") String id) {
        UserEntity userEntity = restTemplate.getForObject(URL + "/get/{id}", UserEntity.class, id);
        return userEntity;
    }

    /**
     * 有参get
     */
    @RequestMapping(value = "/rest/get2/{id}")
    public UserEntity getById2(@PathVariable(name = "id") String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        UserEntity userEntity = restTemplate.getForObject(URL + "/get/{id}", UserEntity.class, params);
        return userEntity;
    }

    /**
     * post
     */
    @RequestMapping(value = "/rest/create", method = RequestMethod.POST)
    public UserEntity create(@RequestBody UserEntity userEntity) {
        ResponseEntity<UserEntity> responseEntity = restTemplate.postForEntity(URL + "/create", userEntity, UserEntity.class);
        return responseEntity.getBody();
    }
}
