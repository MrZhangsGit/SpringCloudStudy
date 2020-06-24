package com.my.rabbitmq.sender.handle.deadQueue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 开启生产者消息确认机制
 # 开启发送确认
 spring.rabbitmq.publisher-confirms=true
 # 开启发送失败退回
 spring.rabbitmq.publisher-returns=true

 开启消费者消息确认机制
 # 开启ACK
 spring.rabbitmq.listener.simple.acknowledge-mode=manual

 @Configuration
 public class RabbitConfig {

     public final static String queueName = "hello_queue";

     //死信队列：
     public final static String deadQueueName = "dead_queue";
     public final static String deadRoutingKey = "dead_routing_key";
     public final static String deadExchangeName = "dead_exchange";

     //死信队列 交换机标识符
     public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
     //死信队列交换机绑定键标识符
     public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

     @Bean
     public Queue helloQueue() {
            //将普通队列绑定到私信交换机上
            Map<String, Object> args = new HashMap<>(2);
            args.put(DEAD_LETTER_QUEUE_KEY, deadExchangeName);
            args.put(DEAD_LETTER_ROUTING_KEY, deadRoutingKey);
            Queue queue = new Queue(queueName, true, false, false, args);
            return queue;
     }

     //死信队列：
     @Bean
     public Queue deadQueue() {
            Queue queue = new Queue(deadQueueName, true);
            return queue;
     }

     @Bean
     public DirectExchange deadExchange() {
            return new DirectExchange(deadExchangeName);
     }

     @Bean
     public Binding bindingDeadExchange(Queue deadQueue, DirectExchange deadExchange) {
            return BindingBuilder.bind(deadQueue).to(deadExchange).with(deadRoutingKey);
     }

 }

 *
 */
@Component
public class HelloSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routingKey) {
        String content = "你好,现在是 " + new Date();
        System.out.println("Send content :" + content);
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.convertAndSend(exchange, routingKey, content);
    }

    /**
     * 确认回调
     */
    @Override
    public void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String cause) {
        if (!ack) {
            System.out.println("send ack fail, cause = " + cause);
        } else {
            System.out.println("send ack success");
        }
    }

    /**
     * 失败后return回调
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("send fail return-message = " + new String(message.getBody()) + ", " +
                "replyCode: " + replyCode + ", replyText: " + replyText + ", " +
                "exchange: " + exchange + ", routingKey: " + routingKey);
    }
}
