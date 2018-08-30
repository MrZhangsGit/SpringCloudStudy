package com.oeasycloud.mykafkaproducer.server;

import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/kafka")
public class ProducerController {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public Response sendKafka(HttpServletRequest request, HttpServletResponse response) {
        try {
            String message = request.getParameter("message");
            LOG.info("kafka的消息={}", message);
            kafkaTemplate.send("test", "key", message);
            LOG.info("发送kafka成功!");
            return new Response();
        } catch (Exception e) {
            LOG.error("发送kafka失败", e);
            return new Response();
        }
    }
}
