package com.oeasycloud.myeurekaclient.restTemplate.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangs
 * @Description 用户实体类
 * @createDate 2019/1/16
 */
@Data
public class UserEntity implements Serializable {
    private String id;

    private String userName;

    private String password;

    private Integer age;

    private String email;
}
