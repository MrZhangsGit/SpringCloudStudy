package com.my.mqtt.consumer.utils.queue;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObjectEventMain {
    public static void main(String[] args) throws Exception{
        /**
         * 创建缓冲池
         */
        ExecutorService executor = Executors.newCachedThreadPool();
        /**
         * 创建工厂
         */
        ObjectEventFactory factory = new ObjectEventFactory();
        /**
         * 创建bufferSize,即RingBuffer大小(必须是2的N次方)
         */
        int ringBufferSize = 1024 * 1024;

        /**
         * BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
         * WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
         * SleepingWaitStrategy 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
         * WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
         * YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
         * WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
         */

        /**
         * 创建disruptor
         */
        Disruptor<ObjectEvent> disruptor =
                new Disruptor<ObjectEvent>(factory, ringBufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
        /**
         * 连接消费事件方法
         */
        disruptor.handleEventsWith(new ObjectEventConsumer());

        /**
         * 启动
         */
        disruptor.start();

        RingBuffer<ObjectEvent> ringBuffer = disruptor.getRingBuffer();
        /*ObjectEventProducer producer = new ObjectEventProducer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        for (long l = 0;l < 100;l++) {
            String content = "===Producer:" + l;
            byteBuffer.putLong(0, l);
            producer.onData(byteBuffer);
        }*/

        ObjectEventProducer producer = new ObjectEventProducer(ringBuffer);
        for (long l = 0;l < 100;l++) {
            String content = "===Producer:" + l;
            producer.onData(content);
        }
    }
}
