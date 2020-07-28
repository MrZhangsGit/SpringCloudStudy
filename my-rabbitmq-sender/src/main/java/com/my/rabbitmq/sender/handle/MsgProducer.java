package com.my.rabbitmq.sender.handle;

import com.alibaba.fastjson.JSON;
import com.my.rabbitmq.sender.config.RabbitMQConfig;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
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

    /**
     * channel 生产者-消费者
     */
    public void send() {
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
             * builder.expiration() 设置每条消息的过期时间(millis)
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
            /**
             * 创建一个频道
             */
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //指定消费队列
            channel.basicConsume(QUEUE_NAME, true, new MyConsumer(channel));
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send2() {
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

    /**
     * topicChannelSend的消费者1
     */
    public void topicChannelSendReceiver1() {
        try {
            String QUEUE_NAME = "topic_channel_receiver1";
            String EXCHANGE_NAME = "test_exchange_topic";
            String ROUTING_KEY = "product.add";
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
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //将队列绑定到交换机上
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY, null);
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try {
                        String msg = new String(body, "utf-8");
                        log.info("topicChannelSendReceiver1 Recv msg: {}", msg);

                        log.info("topicChannelSendReceiver1 done...");
                        //手动应答
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
            log.info("topicChannelSendReceiver1 start...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 以channel编写Topic模式
     */
    public void topicChannelSend() {
        try {
            String EXCHANGE_NAME = "test_exchange_topic";
            String ROUTING_KEY = "product.delete";
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
            /**
             * 声明交换机
             */
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            /**
             * prefetchSize 消息的大小。0即无限制
             * prefetchCount 会告诉RabbitMQ不要同时给一个消费者推送多于N个消息，
             *      即一旦有N个消息还没有ack，则该consumer将block掉，直到有消息ack
             * global：是否将上面设置应用于channel，
             *      简单点说，就是上面限制是channel级别的还是consumer级别
             *
             * 保证一次只发一个
             * 消费者在接收到队列里的消息但没有返回确认结果之前,队列不会将新的消息分发给该消费者
             * 队列中没有被消费的消息不会被删除
             */
            channel.basicQos(0, 1, false);
            String msg = "Hello !";
            /**
             * 发送消息,只发送到路由键为"product.delete" 或者 "product.#"的队列
             */
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes("UTF-8"));
            log.info("--- topicChannelSend Sender Message:{}", msg);

            Thread.sleep(3*1000);
            this.topicChannelSendReceiver1();
            this.topicChannelSendReceiver2();

            channel.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * topicChannelSend的消费者
     */
    public void topicChannelSendReceiver2() {
        try {
            String QUEUE_NAME = "topic_channel_receiver2";
            String EXCHANGE_NAME = "test_exchange_topic";
            String ROUTING_KEY = "product.#";
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
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //将队列绑定到交换机上
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY, null);
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try {
                        String msg = new String(body, "utf-8");
                        log.info("(consumer)Current Thread Name: {}", Thread.currentThread().getName());
                        log.info("topicChannelSendReceiver2 Recv msg: {}", msg);

                        log.info("topicChannelSendReceiver2 done...");
                        //手动应答
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
            log.info("topicChannelSendReceiver2 start...");

            log.info("(main)Current Thread Name: {}", Thread.currentThread().getName());
            Thread.sleep(1000);
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 轮询分发消费(消费者消费的数量相等)
     */
    public void pollingSendConsume() {
        try {
            this.pollingSendConsumeReceiver1();
            this.pollingSendConsumeReceiver2();

            String QUEUE_NAME = "polling_send_consume";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i=0;i<50;i++) {
                String msg = "Hello " + i;
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("UTF-8"));
                log.info("pollingSendConsume Send Msg: {}", msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 轮询分发消费
     */
    public void pollingSendConsumeReceiver1() {
        try {
            String QUEUE_NAME = "polling_send_consume";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try {
                        String msg = new String(body, "utf-8");
                        log.info("pollingSendConsumeReceiver1 Recv msg: {}", msg);
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            /**
             * basicConsume中的autoAck是应答模式
             *  true：自动应答，即消费者获取到消息，该消息就会从队列中删除掉
             *  false：手动应答，当从队列中取出消息后，需要程序员手动调用方法应答，如果没有应答，该消息会一直存在队列中.
             */
            channel.basicConsume(QUEUE_NAME, true,"",false, false, null, consumer);
            log.info("pollingSendConsumeReceiver1 start...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 轮询分发消费
     */
    public void pollingSendConsumeReceiver2() {
        try {
            String QUEUE_NAME = "polling_send_consume";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try {
                        String msg = new String(body, "utf-8");
                        log.info("pollingSendConsumeReceiver2 Recv msg: {}", msg);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            channel.basicConsume(QUEUE_NAME, true,"",false, false, null, consumer);
            log.info("pollingSendConsumeReceiver2 start...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公平分发(消费者消费的越快，消费的数量也就越多)
     */
    public void fairSendConsume() {
        try {
            this.fairSendConsumeReceiver1();
            this.fairSendConsumeReceiver2();

            String QUEUE_NAME = "fair_send_consume";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            /**
             * 每次只向消费者发送一条消息，消费者消费后需手动确认后才会发送另一条
             */
            channel.basicQos(0, 1, false);

            for (int i=0;i<10;i++) {
                String msg = "Hello " + i;
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("UTF-8"));
                log.info("pollingSendConsume Send Msg: {}", msg);
            }

            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公平分发消费
     */
    public void fairSendConsumeReceiver1() {
        try {
            String QUEUE_NAME = "fair_send_consume";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicQos(0, 1, false);

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try {
                        String msg = new String(body, "utf-8");
                        log.info("fairSendConsumeReceiver1 Recv msg: {}", msg);
                        Thread.sleep(3000);
                        /**
                         * 手动确认
                         * false表示只确认当前这条消息已收到
                         * true表示在当前这条消息及之前(小于 DeliveryTag )的所有未确认的消息都已收到.
                         */
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            channel.basicConsume(QUEUE_NAME, false,"",false, false, null, consumer);
            log.info("fairSendConsumeReceiver1 start...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公平分发消费
     */
    public void fairSendConsumeReceiver2() {
        try {
            String QUEUE_NAME = "fair_send_consume";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicQos(0, 1, false);

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try {
                        String msg = new String(body, "utf-8");
                        log.info("fairSendConsumeReceiver2 Recv msg: {}", msg);
                        Thread.sleep(500);
                        /**
                         * 手动确认
                         * false表示只确认当前这条消息已收到
                         * true表示在当前这条消息及之前(小于 DeliveryTag )的所有未确认的消息都已收到.
                         */
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            channel.basicConsume(QUEUE_NAME, false,"",false, false, null, consumer);
            log.info("fairSendConsumeReceiver2 start...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅者模式。利用交换机,将消息"发送"到多个队列
     * 注意：若RabbitMQ中无该exchange则需手动创建一个，否则会在第一次链接发送时报错
     */
    public void fanoutSendConsume() {
        try {
            String ExchangeName = "test_exchange_fanout";

            this.fanoutSendConsumeReceiver1();
            this.fanoutSendConsumeReceiver2();

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //声明交换机，第二个参数为交换机类型
            channel.exchangeDeclare(ExchangeName, "fanout", false, false, null);

            for (int i=0;i<20;i++) {
                String msg = "Hello " + i;
                channel.basicPublish(ExchangeName, "", null, msg.getBytes("UTF-8"));
                log.info("fanoutSendConsume Send Msg: {}", msg);
            }

            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fanoutSendConsumeReceiver1() {
        try {
            String QueueName = "test_exchange_queue1";
            String ExchangeName = "test_exchange_fanout";

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QueueName, false, false, false, null);
            //将队列绑定到交换机上
            channel.queueBind(QueueName, ExchangeName, "", null);

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try {
                        String msg = new String(body, "utf-8");
                        log.info("fanoutSendConsumeReceiver1 Recv msg: {}", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            channel.basicConsume(QueueName, true,"",false, false, null, consumer);
            log.info("fanoutSendConsumeReceiver1 start...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void fanoutSendConsumeReceiver2() {
        try {
            String QueueName = "test_exchange_queue2";
            String ExchangeName = "test_exchange_fanout";

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QueueName, false, false, false, null);
            //将队列绑定到交换机上
            channel.queueBind(QueueName, ExchangeName, "", null);

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try {
                        String msg = new String(body, "utf-8");
                        log.info("fanoutSendConsumeReceiver2 Recv msg: {}", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            channel.basicConsume(QueueName, true,"",false, false, null, consumer);
            log.info("fanoutSendConsumeReceiver2 start...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 路由模式(direct)
     */
    public void directSendConsume() {
        try {
            String ExchangeName = "test_exchange_direct";

            this.directSendConsumeReceiver1();
            this.directSendConsumeReceiver2();

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //声明交换机，第二个参数为交换机类型
            channel.exchangeDeclare(ExchangeName, "direct", false, false, null);

            String msg1 = "Hello Direct MQ...refuge";
            /**
             * 把消息发送到交换机，交换机再转发到包含路由键"refuge"的队列
             */
            channel.basicPublish(ExchangeName, "refuge", null, msg1.getBytes("UTF-8"));
            log.info("directSendConsume Send Msg: {}", msg1);

            String msg2 = "Hello Direct MQ...wjire";
            channel.basicPublish(ExchangeName, "wjire", null, msg2.getBytes("UTF-8"));
            log.info("directSendConsume Send Msg: {}", msg2);

            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void directSendConsumeReceiver1() {
        try {
            String QueueName = "test_direct_queue1";
            String ExchangeName = "test_exchange_direct";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QueueName, false, false, false, null);

            /**
             * 将队列绑定到交换机上，路由键为"wjire"
             */
            channel.queueBind(QueueName, ExchangeName, "wjire", null);
            /**
             * (等价写法)声明队列
             */
            //channel.queueDeclare(QueueName, false, false, false, null);

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try {
                        String msg = new String(body, "utf-8");
                        log.info("directSendConsumeReceiver1 Recv msg: {}", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            channel.basicConsume(QueueName, true,"",false, false, null, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void directSendConsumeReceiver2() {
        try {
            String QueueName = "test_direct_queue2";
            String ExchangeName = "test_exchange_direct";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QueueName, false, false, false, null);

            /**
             * 将队列绑定到交换机上，该队列匹配两个路由键"refuge"和"wjire"
             */
            channel.queueBind(QueueName, ExchangeName, "wjire", null);
            channel.queueBind(QueueName, ExchangeName, "refuge", null);
            /**
             * (等价写法)声明队列
             */
            //channel.queueDeclare(QueueName, false, false, false, null);

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try {
                        String msg = new String(body, "utf-8");
                        log.info("directSendConsumeReceiver2 Recv msg: {}", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            channel.basicConsume(QueueName, true,"",false, false, null, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
