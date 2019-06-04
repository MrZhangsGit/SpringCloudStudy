package com.oeasycloud.myredisserver.config;

import com.oeasycloud.myredisserver.service.impl.RedisReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author zhangs
 * @Description 使用Redis进行发布订阅
 *      redis消息监听配置
 * @createDate 2019/6/4
 */
@Configuration
public class RedisListenerConfig {

    /**
     * redis消息监听器
     * 可添加多个监听不同话题的redis监听器，
     * 只需把消息和相应的消息订阅处理器绑定
     * 该消息监听器通过反射技术调用消息订阅处理器的方法进行处理
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        System.out.println("Message Listener container...");
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        /**
         * 将消息和相应的消息订阅处理器绑定
         * 可以绑定多个
         */
        container.addMessageListener(listenerAdapter, new PatternTopic("index"));
        return container;
    }

    /**
     * 消息监听适配器，绑定消息处理器(利用反射技术调用处理器的业务方法)
     */
    @Bean
    MessageListenerAdapter listenerAdapter(RedisReceiver redisReceiver) {
        System.out.println("Message Adapter...");

        /**
         * 注明处理类以及处理方法
         */
        return new MessageListenerAdapter(redisReceiver, "receiveMessage");
        /*return new MessageListenerAdapter(redisReceiver, "other");*/
    }

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
