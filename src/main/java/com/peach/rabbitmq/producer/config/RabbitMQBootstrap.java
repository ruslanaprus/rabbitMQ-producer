package com.peach.rabbitmq.producer.config;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQBootstrap implements ApplicationListener<ApplicationReadyEvent> {

    private final RabbitAdmin rabbitAdmin;

    public RabbitMQBootstrap(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        rabbitAdmin.initialize();
    }
}