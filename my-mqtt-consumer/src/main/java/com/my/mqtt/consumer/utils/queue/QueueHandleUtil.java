package com.my.mqtt.consumer.utils.queue;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class QueueHandleUtil {

    /**
     * 创建缓冲池
     */
    ExecutorService executor = Executors.newCachedThreadPool();

    public void advanceDisruptor(String content) {
        /**
         * 创建工厂
         */
        ObjectEventFactory factory = new ObjectEventFactory();
        /**
         * 创建bufferSize,即RingBuffer大小(必须是2的N次方)
         */
        int ringBufferSize = 1024 * 1024;
        /**
         * 创建disruptor
         */
        Disruptor<ObjectEvent> disruptor =
                new Disruptor<ObjectEvent>(factory, ringBufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
        RingBuffer<ObjectEvent> ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(new ObjectEventConsumer());
        disruptor.start();
        ObjectEventProducer producer = new ObjectEventProducer(ringBuffer);
        log.info("MQ消费者将接收的消息放入Disruptor===content:" + content);
        producer.onData(content);
    }

    public void advanceDisruptor2(String content) {
        /**
         * 创建工厂
         */
        ObjectEventFactory factory = new ObjectEventFactory();
        /**
         * 创建bufferSize,即RingBuffer大小(必须是2的N次方)
         */
        int ringBufferSize = 1024 * 1024;
        /**
         * 创建disruptor
         */
        Disruptor<ObjectEvent> disruptor =
                new Disruptor<ObjectEvent>(factory, ringBufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
        RingBuffer<ObjectEvent> ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(new ObjectEventConsumer2());
        disruptor.start();
        ObjectEventProducer producer = new ObjectEventProducer(ringBuffer);
        log.info("MQ消费者将接收的消息放入Disruptor2===content:" + content);
        producer.onData(content);
    }
}
