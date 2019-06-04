package com.oeasycloud.myredisserver.service.impl;

import org.springframework.stereotype.Service;

/**
 * @author zhangs
 * @Description redis消息处理
 * @createDate 2019/6/4
 */
@Service
public class RedisReceiver {
    public void receiveMessage(String message) {
        System.out.println("Message is Received:" + message);
    }

    public void other(String message) {
        System.out.println("Message is Received(other):" + message);
    }
}
