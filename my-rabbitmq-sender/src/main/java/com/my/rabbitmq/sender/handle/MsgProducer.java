package com.my.rabbitmq.sender.handle;

import com.my.rabbitmq.sender.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/10/18
 */
@Component
@Slf4j
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    /**
     * 由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
     */
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入RabbitTemplate
     * @param rabbitTemplate
     */
    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        /**
         * rabbitTemplate如果为单例的话，那回调就是最后设置的内容
         */
        rabbitTemplate.setConfirmCallback(this::confirm);
    }

    /*public void sendMsg(String content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        *//**
         * 将消息放入ROUTINGKEY_A对应的队列当中，对应的队列是A
         *//*
        //rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_A, RabbitMQConfig.ROUTINGKEY_A, content, correlationData);
        rabbitTemplate.convertSendAndReceive(RabbitMQConfig.TOPIC_EXCHANGE_NAME, RabbitMQConfig.TOPIC_ROUTING_KEY, content, correlationData);
    }*/

    public void sendTopicMsg(String content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        /**
         * 将消息放入ROUTINGKEY_A对应的队列当中，对应的队列是A
         * 在RabbitMQ的队列中已设置队列上的超时时间
         */
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, RabbitMQConfig.TOPIC_ROUTING_KEY, content, correlationData);
    }

    public void sendTopicMsgWithActive(String content) {
        MessagePostProcessor messagePostProcessor = new MyMessagePostProcessor(3 * 1000);
        /**
         * 在RabbitMQ的队列中已设置队列上的超时时间
         */
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, RabbitMQConfig.TOPIC_ROUTING_KEY, content, messagePostProcessor);
    }

    public void sendDirectMsg(String content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        /**
         * 将消息放入ROUTINGKEY_A对应的队列当中，对应的队列是A
         */
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE_NAME, RabbitMQConfig.DIRECT_ROUTING_KEY, content, correlationData);
    }

    /**
     * 回调
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info(" 回调Id:{}", correlationData);
        if (ack) {
            log.info("消息发送成功");
        } else {
            log.info("消息发送失败:{}", cause);
        }
    }
}
