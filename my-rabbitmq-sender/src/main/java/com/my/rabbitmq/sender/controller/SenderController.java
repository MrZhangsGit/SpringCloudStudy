package com.my.rabbitmq.sender.controller;

import com.my.rabbitmq.sender.config.RabbitMQConfig;
import com.my.rabbitmq.sender.handle.MsgProducer;
import com.my.rabbitmq.sender.handle.deadQueue.HelloSender;
import com.my.rabbitmq.sender.po.YardBasicBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
     *      队列以及消息的有效期以及持久化设置
     * @param yardBasicBO
     */
    @PostMapping("/send")
    public void send(@RequestBody YardBasicBO yardBasicBO) {
        msgProducer.send();
    }

    @PostMapping("/send2")
    public void send() {
        msgProducer.send2();
    }


    //死信队列
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


    /**
     * 以channel编写Topic模式
     */
    @PostMapping("/topicChannelSend")
    public void topicChannelSend() {
        msgProducer.topicChannelSend();
    }
    /**
     * 轮询分发消费(消费者消费的数量相等)
     */
    @PostMapping("/pollingSendConsume")
    public void pollingSendConsume() {
        msgProducer.pollingSendConsume();
    }
    /**
     * 公平分发(消费者消费的越快，消费的数量也就越多)
     */
    @PostMapping("/fairSendConsume")
    public void fairSendConsume() {
        msgProducer.fairSendConsume();
    }
    /**
     * 订阅者模式。利用交换机,将消息"发送"到多个队列
     */
    @PostMapping("/fanoutSendConsume")
    public void fanoutSendConsume() {
        msgProducer.fanoutSendConsume();
    }
    /**
     * 路由模式(direct)
     */
    @PostMapping("/directSendConsume")
    public void directSendConsume() {
        msgProducer.directSendConsume();
    }
}
