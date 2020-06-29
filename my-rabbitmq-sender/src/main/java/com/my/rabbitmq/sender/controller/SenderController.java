package com.my.rabbitmq.sender.controller;

import com.alibaba.fastjson.JSON;
import com.my.rabbitmq.sender.config.RabbitMQConfig;
import com.my.rabbitmq.sender.handle.MsgProducer;
import com.my.rabbitmq.sender.handle.MyConsumer;
import com.my.rabbitmq.sender.handle.deadQueue.HelloSender;
import com.my.rabbitmq.sender.po.YardBasicBO;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class SenderController {
    @Autowired
    private MsgProducer msgProducer;

    @PostMapping("/topicSend")
    public void topicSend(@RequestBody YardBasicBO yardBasicBO) {
        msgProducer.sendTopicMsg("First Message without Active");
        msgProducer.sendTopicMsgWithActive("Second Message with Active");
    }

    /**
     * channel 生产者-消费者
     * @param yardBasicBO
     */
    @PostMapping("/send")
    public void send(@RequestBody YardBasicBO yardBasicBO) {
        try {
            /**
             * 队列名称
             */
            String QUEUE_NAME = "send";
            /**
             * 创建连接连接RabbitMQ
             */
            ConnectionFactory factory = new ConnectionFactory();
            /**
             * 设置RabbitMQ所在主机ip
             */
            factory.setHost("127.0.0.1");
            /**
             * 创建一个连接
             */
            Connection connection = factory.newConnection();
            /**
             * 创建一个频道
             * channel.queueDeclare()中的durable属性是指队列的持久化
             */
            String content = JSON.toJSONString(yardBasicBO);
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            /**
             * builder.deliveryMode(设置消息持久化 1-不持久化|2-持久化)
             */
            builder.deliveryMode(2);
            AMQP.BasicProperties properties = builder.build();
            channel.basicPublish("", QUEUE_NAME, properties, "First Message".getBytes("UTF-8"));
            log.info("--- Sender Message:{}", "First Message");
            channel.close();

            AMQP.BasicProperties.Builder builder2 = new AMQP.BasicProperties.Builder();
            /**
             * builder.expiration() 设置每条消息的过期时间
             */
            builder2.expiration("10000");
            builder2.deliveryMode(2);
            AMQP.BasicProperties properties2 = builder2.build();
            /**
             * 指定一个队列
             */
            Channel channel2 = connection.createChannel();
            channel2.queueDeclare(QUEUE_NAME, false, false, false, null);
            /**
             * 向队列中发送消息
             */
            channel2.basicPublish("", QUEUE_NAME, properties2, "Second Message".getBytes("UTF-8"));
            log.info("--- Sender Message:{}", "Second Message");

            Thread.sleep(5000);

            this.receiver();

            /**
             * 关闭频道和连接
             */
            channel2.close();
            connection.close();
        } catch (Exception e) {
            log.error("Error:{}", e);
        }
    }

    public void receiver() {
        try {
            /**
             * 队列名称
             */
            String QUEUE_NAME = "send";
            /**
             * 创建连接连接RabbitMQ
             */
            ConnectionFactory factory = new ConnectionFactory();
            /**
             * 设置RabbitMQ所在主机ip
             */
            factory.setHost("127.0.0.1");
            /**
             * 创建一个连接
             */
            Connection connection = factory.newConnection();
            /*String exchangeName = "test_consumer_exchange";
            String routingKey = "consumer.#";
            String queueName = "test_consumer_queue";*/
            /**
             * 创建一个频道
             */
            Channel channel = connection.createChannel();
            /*channel.exchangeDeclare(exchangeName, "topic", true, false, null);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);*/
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //指定消费队列
            channel.basicConsume(QUEUE_NAME, true, new MyConsumer(channel));
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/send2")
    public void send() {
        try {
            /**
             * 队列名称
             */
            String QUEUE_NAME = "send2";
            /**
             * 创建连接连接RabbitMQ
             */
            ConnectionFactory factory = new ConnectionFactory();
            /**
             * 设置RabbitMQ所在主机ip
             */
            factory.setHost("127.0.0.1");
            /**
             * 创建一个连接
             */
            Connection connection = factory.newConnection();
            /**
             * 创建一个频道
             */
            Channel channel = connection.createChannel();

            Map<String, Object> map = new HashMap<String , Object>();
            //设置队列里消息的ttl的时间30s(队列里面所有消息的有效时间)
            map.put("x-message-ttl" , 30 * 1000);
            /**
             * 指定一个队列
             */
            channel.queueDeclare(QUEUE_NAME, true, false, false, map);
            /**
             * 向队列中发送消息
             */
            channel.basicPublish("", QUEUE_NAME, null, " Message".getBytes("UTF-8"));
            log.info("--- Sender Message:{}", " Message");
            /**
             * 关闭频道和连接
             */
            channel.close();
            connection.close();
        } catch (Exception e) {
            log.error("Error:{}", e);
        }
    }

    @Autowired
    private HelloSender helloSender;

    /**
     * 1、exchange, queue 都正确, confirm被回调, ack=true
     */
    @RequestMapping("/DeadQueueSend1")
    public void send1() {
        helloSender.send(null, RabbitMQConfig.QUEUE_NAME_WITH_DEAD);
    }

    /**
     * 2、exchange 错误, queue 正确, confirm被回调, ack=false
     */
    @RequestMapping("/DeadQueueSend2")
    public void send2() {
        helloSender.send("fail-exchange", RabbitMQConfig.QUEUE_NAME_WITH_DEAD);
    }

    /**
     * 3、exchange 正确, queue 错误, confirm被回调, ack=true; return被回调 replyText:NO_ROUTE
     */
    @RequestMapping("/DeadQueueSend3")
    public void send3() {
        helloSender.send(null, "fail-queue");
    }

    /**
     * 4、exchange 错误, queue 错误, confirm被回调, ack=false
     */
    @RequestMapping("/DeadQueueSend4")
    public void send4() {
        helloSender.send("fail-exchange", "fail-queue");
    }
}
