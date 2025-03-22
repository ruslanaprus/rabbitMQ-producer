package com.peach.rabbitmq.producer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FixedRateProducer {
    private static final Logger log = LoggerFactory.getLogger(FixedRateProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public FixedRateProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private int i = 0;

    @Scheduled(fixedRate = 1000)
    public void sendMessage(){
        i++;
        if (i <= 20) {  // Stop after 50 messages
            log.info("i is: {}", i);
            rabbitTemplate.convertAndSend("peach.fixrate", "fixed rate: " + i);
        }
    }
}
