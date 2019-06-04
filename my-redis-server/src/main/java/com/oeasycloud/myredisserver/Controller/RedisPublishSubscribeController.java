package com.oeasycloud.myredisserver.Controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/6/4
 */
@EnableScheduling
@Component
public class RedisPublishSubscribeController {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendMessage(){
        stringRedisTemplate.convertAndSend("index",String.valueOf(Math.random()));
    }
}
