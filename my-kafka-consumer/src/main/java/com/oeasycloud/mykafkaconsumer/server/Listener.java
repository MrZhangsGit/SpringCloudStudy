package com.oeasycloud.mykafkaconsumer.server;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * 简单的读取并打印key和message值
 */
public class Listener {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * @KafkaListener中topics属性用于指定kafka topic名称，topic名称由消息生产者指定，
     * 也就是由kafkaTemplate在发送消息时指定。
     */
    @KafkaListener(topics = {"test"})
    public void listener(ConsumerRecord<?, ?> record) {
        LOG.info("kafka的key: " + record.key());
        LOG.info("kafka的value: " + record.value().toString());
    }
}
