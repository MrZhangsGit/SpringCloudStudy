package com.my.rabbitmq.sender.controller;

import com.alibaba.fastjson.JSON;
import com.my.rabbitmq.sender.handle.MsgProducer;
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
}
