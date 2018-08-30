package com.my.mqtt.consumer.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServiceInfoUtil implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
    private static EmbeddedServletContainerInitializedEvent event;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        ServiceInfoUtil.event = event;
    }

    public int getPort() {
        int port = event.getEmbeddedServletContainer().getPort();
        return port;
    }

}
