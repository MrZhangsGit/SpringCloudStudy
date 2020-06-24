package com.my.rabbitmq.sender.controller;

import com.alibaba.fastjson.JSON;
import com.my.rabbitmq.sender.config.RabbitMQConfig;
import com.my.rabbitmq.sender.handle.MsgProducer;
import com.my.rabbitmq.sender.handle.deadQueue.HelloSender;
import com.my.rabbitmq.sender.po.YardBasicBO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class SenderController {
    @Autowired
    private MsgProducer msgProducer;

    @PostMapping("/send")
    public void send(@RequestBody YardBasicBO yardBasicBO) {
        System.out.println("---start---:" + yardBasicBO);
        msgProducer.sendTopicMsg(JSON.toJSONString(yardBasicBO));
        /*try {
            String QUEUE_NAME = "SEND1";
            *//**
             * 创建连接连接RabbitMQ
             *//*
            ConnectionFactory factory = new ConnectionFactory();
            *//**
             * 设置RabbitMQ所在主机ip
             *//*
            factory.setHost("localhost");
            *//**
             * 创建一个连接
             *//*
            Connection connection = factory.newConnection();
            *//**
             * 创建一个频道
             *//*
            Channel channel = connection.createChannel();
            *//**
             * 指定一个队列
             *//*
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            *//**
             * 向队列中发送消息
             *//*
            channel.basicPublish("", QUEUE_NAME, null, content.getBytes());
            log.info("--- Sender Message:{}", content);
            *//**
             * 关闭频道和连接
             *//*
            channel.close();
            connection.close();
        } catch (Exception e) {
            log.error("Error:{}", e);
        }*/
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
