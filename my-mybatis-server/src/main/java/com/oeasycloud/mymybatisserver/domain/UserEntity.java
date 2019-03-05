package com.oeasycloud.mymybatisserver.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/2/27
 */
@Data
public class UserEntity implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private Long id;
    private String userName;
    private String passWord;
}
