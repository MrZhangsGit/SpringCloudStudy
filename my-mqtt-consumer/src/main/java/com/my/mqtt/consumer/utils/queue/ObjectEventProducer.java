package com.my.mqtt.consumer.utils.queue;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 1.建Event类（数据对象）
 * 2.建立一个生产数据的工厂类，EventFactory，用于生产数据；
 * 3.监听事件类（处理Event数据）
 * 4.实例化Disruptor，配置参数，绑定事件；
 * 5.建存放数据的核心 RingBuffer，生产的数据放入 RungBuffer。
 *
 * 当用一个简单队列来发布事件的时候会牵涉更多的细节，这是因为事件对象还需要预先创建。
 * 发布事件最少需要两步：获取下一个事件槽并发布事件（发布事件的时候要使用try/finnally保证事件一定会被发布）。
 * 如果我们使用RingBuffer.next()获取一个事件槽，那么一定要发布对应的事件。
 * 如果不能发布事件，那么就会引起Disruptor状态的混乱。
 * 尤其是在多个事件生产者的情况下会导致事件消费者失速，从而不得不重启应用才能会恢复。
 */
public class ObjectEventProducer {
    private final RingBuffer<ObjectEvent> ringBuffer;

    public ObjectEventProducer(RingBuffer<ObjectEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * onData用来发布事件，每调用一次就发布一次事件
     */
    public void onData(ByteBuffer byteBuffer) {
        /**
         * 1.可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
         */
        long sequence = ringBuffer.next();
        try {
            /**
             * 用上面的索引取出一个空的时间用于填充(获取该序号对应的事件对象)
             */
            ObjectEvent event = ringBuffer.get(sequence);
            /**
             * 获取要通过事件传递的业务数据
             */
            event.setEvent(byteBuffer.getLong(0));
        } finally {
            /**
             * 发布事件
             * 最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；
             * 如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
             */
            ringBuffer.publish(sequence);
        }
    }

    public void onData(String content) {
        long sequence = ringBuffer.next();
        try {
            ObjectEvent event = ringBuffer.get(sequence);
            event.setEvent(content);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
