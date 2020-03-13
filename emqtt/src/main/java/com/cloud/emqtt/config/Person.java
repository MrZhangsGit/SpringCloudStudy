package com.cloud.emqtt.config;

import lombok.Data;

import java.sql.Date;

/**
 * @module: com.berl.emqtt.config
 * @author: chenfei
 * @date: 2018 2018/12/18
 * @version: v1.0
 */
@Data
public class Person {
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private Date brithday;
}
