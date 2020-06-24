package com.my.rabbitmq.sender.handle.deadQueue;

import com.my.rabbitmq.sender.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_WITH_DEAD)
public class HelloReceiver {

    @RabbitHandler
    public void process(String content, Channel channel, Message message){
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            /**
             * 告诉服务器收到这条消息已经被这边消费了，可以在队列删掉。
             * 否则消息服务器以为这条消息没处理掉后续还会再发
             *
             * channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
             * 添加这行代码，测试结果：消息正常消费，消息从队列中删除。
             * 注释掉该行代码，测试结果：消息会被重复消费，一直保留在队列(RabbitMQConfig.QUEUE_NAME_WITH_DEAD)中。
             */
            //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            /**
             * channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
             * 执行该代码时(消息被正常消费)消息会被加入到死信队列(RabbitMQConfig.DEAD_QUEUE_NAME)
             */
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            System.out.println("Receiver Success : " + content);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Receiver Fail!");
        }
    }
}
