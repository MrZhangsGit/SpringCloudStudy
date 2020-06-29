package com.my.rabbitmq.sender.handle;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义消费者
 */
@Slf4j
public class MyConsumer extends DefaultConsumer{
    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        log.info("--- Consumer Message:{}", new String(body));
    }
}
