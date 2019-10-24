package com.my.rabbitmq.sender.handle;

import com.alibaba.fastjson.JSON;
import com.my.rabbitmq.sender.config.RabbitMQConfig;
import com.my.rabbitmq.sender.po.YardBasicBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/10/18
 */
@Slf4j
@Component
public class MsgReceiver {

    @RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE_NAME)
    public void topicProcess(String content) {
        log.info("Topic---接收处理队列A当中的消息:{}", content);
        YardBasicBO yardBasic = JSON.parseObject(content, YardBasicBO.class);
        System.out.println(JSON.toJSONString(yardBasic));
    }

    @RabbitListener(queues = RabbitMQConfig.DIRECT_QUEUE_NAME)
    public void directProcess(String content) {
        log.info("Direct---接收处理队列A当中的消息:{}", content);
    }
}
