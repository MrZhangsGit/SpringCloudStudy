package com.oeasycloud.myeurekaclient.restTemplate.controller;

import com.alibaba.fastjson.JSON;
import com.oeasycloud.myeurekaclient.restTemplate.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangs
 * @Description restTemplateDemo
 *      RestTemplate是Spring提供的用于访问Rest服务的客户端
 * @createDate 2019/1/16
 */
@RestController
@Slf4j
public class RestTemplateAsyncController {

    AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

    private static String URL = "http://192.168.2.190:8762";

    /**
     * 无参的get请求
     */
    @RequestMapping(value = "/rest/async/getAll", method = RequestMethod.POST)
    public void getAll() {
        //调用完后立即返回（没有阻塞）
        ListenableFuture<ResponseEntity<List>> forEntity = asyncRestTemplate.getForEntity(URL + "/getAll", List.class);
        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<List>>() {

            @Override
            public void onSuccess(ResponseEntity<List> listResponseEntity) {
                System.out.println("AsyncRestTemplate Response Success.Result:" + JSON.toJSONString(listResponseEntity.getBody()));
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("AsyncRestTemplate Response Failure.E:" + JSON.toJSONString(throwable));
            }
        });
        return;
    }

    /**
     * post 回调有参
     */
    /*@RequestMapping(value = "/rest/async/create", method = RequestMethod.POST)
    public void create() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        UserEntity userEntity = new UserEntity();
        userEntity.setId("123");
        userEntity.setUserName("zs123");
        HttpEntity<Object> httpEntity = new HttpEntity<>(userEntity, headers);
        ListenableFuture<ResponseEntity<UserEntity>> forEntity = asyncRestTemplate.postForEntity(URL + "/create", httpEntity, UserEntity.class);
        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<UserEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("AsyncRestTemplate Response Failure.E:" + throwable);
            }

            @Override
            public void onSuccess(ResponseEntity<UserEntity> userEntityResponseEntity) {
                System.out.println("AsyncRestTemplate Response Success.Result:" + JSON.toJSONString(userEntityResponseEntity.getBody()));
            }
        });
    }*/

    /**
     * post 回调无参
     */
    /*@RequestMapping(value = "/rest/async/create2", method = RequestMethod.POST)
    public void create2() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        UserEntity userEntity = new UserEntity();
        userEntity.setId("123");
        userEntity.setUserName("zs123");
        HttpEntity<Object> httpEntity = new HttpEntity<>(userEntity, headers);

        ListenableFuture<ResponseEntity<UserEntity>> forEntity = asyncRestTemplate.exchange(URL + "/create", HttpMethod.POST, httpEntity, UserEntity.class);
        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<UserEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("AsyncRestTemplate Response Failure.E:" + throwable);
            }

            @Override
            public void onSuccess(ResponseEntity<UserEntity> userEntityResponseEntity) {
                System.out.println("AsyncRestTemplate Response Success.Result:" + JSON.toJSONString(userEntityResponseEntity.getBody()));
            }
        });
        System.out.println("=================asyncRestTemplate POST Is Over!");
        ListenableFuture<ResponseEntity<List>>  forEntityGet= asyncRestTemplate.exchange(URL + "/getAll", HttpMethod.POST, httpEntity, List.class);
        System.out.println("=================asyncRestTemplate GET Is Over!");
    }*/

    /**
     * 异步回调
     * @param
     * @author zhangs
     *
     * @createDate 2019/8/6
     */
    public void asyncRequestForCallback(String method, String url, Object headerJson, Object entity) {
        log.info("{}::异步回调 Start::method:{}, url:{}, headerJson:{}, entity:{}",
                Thread.currentThread().getStackTrace()[1].getMethodName(), method, url, headerJson, JSON.toJSONString(entity));
        if (StringUtils.isEmpty(method)) {
            return;
        }
        if (StringUtils.isEmpty(url)) {
            return;
        }

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        if (headerJson != null) {
            String headerJsonStr = String.valueOf(headerJson);
            Map<String, Object> map = JSON.parseObject(headerJsonStr, Map.class);
            if (!CollectionUtils.isEmpty(map)) {
                for (String key:map.keySet()) {
                    headers.add(key, String.valueOf(map.get(key)));
                }
            }
        }
        HttpEntity<Object> httpEntity = new HttpEntity<>(entity, headers);

        HttpMethod httpMethod = HttpMethod.resolve(method.toUpperCase());

        ListenableFuture<ResponseEntity<Object>> forEntity =
                asyncRestTemplate.exchange(url, httpMethod, httpEntity, Object.class);
        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("AsyncRestTemplate Response Failure.E::{}", JSON.toJSONString(throwable));
            }

            @Override
            public void onSuccess(ResponseEntity<Object> entityResponseEntity) {
                log.info("AsyncRestTemplate Response Success.Result::{}", JSON.toJSONString(entityResponseEntity.getBody()));
            }
        });
    }
}
