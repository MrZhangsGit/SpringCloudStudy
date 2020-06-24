package com.my.rabbitmq.sender.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/10/18
 */
@Configuration
@Slf4j
public class RabbitMQConfig {
    /**
     * Broker: 提供一种传输服务，它的角色是维护一条从生产着到消费者间的路线，保证数据能按照指定的方式进行传输
     * Exchange: 消息交换机，它指定消息按什么规则、路由到哪个队列
     * Queue: 消息的载体，每个消息都会被投到一个或多个队列
     * Binding: 绑定，作用是把Exchange和Queue按照路由规则绑定起来
     * Routing Key: 路由关键字，exchange根据这个关键字进行消息投递
     * vHost: 虚拟主机，一个broker里可以有多个vhost，用做不同用户的权限分离
     * Producer: 消息生产者
     * Consumer: 消息消费者
     * Channel: 消息通道，在客户端的每个连接里可以建立多个channel
     */

    private String host = "127.0.0.1";

    private int port = 5672;

    private String username = "guest";

    private String password = "guest";

    public static final String DIRECT_QUEUE_NAME = "queue_rpc_test";
    public static final String TOPIC_QUEUE_NAME = "queue_local_car";
    /**
     * 配置死信队列的队列名
     */
    public static final String QUEUE_NAME_WITH_DEAD = "hello_queue_with_dead";
    /**
     * 死信队列
     */
    public static final String DEAD_QUEUE_NAME = "dead_queue";

    public static final String DIRECT_EXCHANGE_NAME = "exchange_direct_rpc";
    public static final String TOPIC_EXCHANGE_NAME = "exchange_topic_as_car";
    public static final String DEAD_EXCHANGE_NAME = "dead_exchange";

    public static final String DIRECT_ROUTING_KEY = "rpc";
    public static final String TOPIC_ROUTING_KEY = "topic.car.*";
    public static final String DEAD_ROUTING_KEY = "dead_routing_key";

    /**
     * 死信队列 交换机标识符
     */
    public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(host, port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPublisherConfirms(true);
        return factory;
    }

    /**
     * 必须是SCOPE_PROTOTYPE类型
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    /**
     * 把交换机、队列，通过路由关键字进行绑定
     *
     * 针对消费者配置
     * 1.设置交换机
     * 2.将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingKey的概念
     * HeadersExchange: 通过添加属性key-value匹配
     * DirectExchange: 按照routingKey分发到指定队列
     * TopicExchange: 多关键字匹配
     */
    @Bean
    public DirectExchange defaultDirectExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME, true, false);
    }
    @Bean
    public TopicExchange defaultTopicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
    }

    /**
     * 获取队列
     * 队列持久化
     */
    @Bean
    public Queue queueTopic() {
        Map<String, Object> args = new HashMap<String, Object>();
        //通过队列属性设置加入该队列的消息的过期时间，单位为ms.
        args.put("x-message-ttl", 2 * 60 * 60 * 1000);
        return new Queue(TOPIC_QUEUE_NAME, true, false, false, args);
    }
    @Bean
    public Queue queueDirect() {
        Map<String, Object> args = new HashMap<String, Object>();
        //通过队列属性设置加入该队列的消息的过期时间，单位为ms.
        args.put("x-message-ttl", 2 * 60 * 60 * 1000);
        return new Queue(DIRECT_QUEUE_NAME, true, false, false, args);
    }

    /**
     * 一个交换机可以绑定多个消息队列，即消息通过一个交换机可以分发到不同的队列中
     * 注:此处bind方法中将queue、exchange以及routingKey进行绑定，所以在消费者处@RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE_NAME)
     *   中只需监听队列即可。此处也可不进行绑定而在消费者的@RabbitListener中进行
     */
    @Bean
    public Binding topicExchangeBinding() {
        log.info("{}---topicExchangeBinding", Thread.currentThread().getStackTrace()[1].getMethodName());
        /**
         * .bind(queueTopic()).to(defaultTopicExchange()).with(RabbitMQConfig.TOPIC_ROUTING_KEY):
         *    将queue、exchange以及routingKey进行绑定
         *    每个queue与exchange进行绑定就需要写一个对应的bind方法
         *    routingKey与MQTT中的topic作用类似，即在同一个exchange以及同一个queue中多个消息可以使用routingKey进行匹配
         *      订阅。此处个人感觉RabbitMQ是MQTT进行了(信道)拆分扩展
         */
        return BindingBuilder.bind(queueTopic()).to(defaultTopicExchange()).with(RabbitMQConfig.TOPIC_ROUTING_KEY);
    }
    @Bean
    public Binding directExchangeBinding() {
        log.info("{}---directExchangeBinding", Thread.currentThread().getStackTrace()[1].getMethodName());
        return BindingBuilder.bind(queueDirect()).to(defaultDirectExchange()).with(RabbitMQConfig.DIRECT_ROUTING_KEY);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue helloWithDeadQueue() {
        //将普通队列绑定到死信交换机上
        Map map = new HashMap<>(2);
        map.put(DEAD_LETTER_QUEUE_KEY, DEAD_EXCHANGE_NAME);
        map.put(DEAD_LETTER_ROUTING_KEY, DEAD_ROUTING_KEY);
        Queue queue = new Queue(QUEUE_NAME_WITH_DEAD, true, false, false, map);
        return queue;
    }
    @Bean
    public Queue deadQueue() {
        Queue queue = new Queue(DEAD_QUEUE_NAME, true);
        return queue;
    }
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE_NAME);
    }
    @Bean
    public Binding bindingDeadExchange(Queue deadQueue, DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(DEAD_ROUTING_KEY);
    }
}
