package com.peach.rabbitmq.producer.scheduler;

import com.peach.rabbitmq.producer.client.RabbitmqClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RabbitmqScheduler {

    private static final Logger log = LoggerFactory.getLogger(RabbitmqScheduler.class);

    private RabbitmqClient rabbitmqClient;

    public RabbitmqScheduler(RabbitmqClient rabbitmqClient) {
        this.rabbitmqClient = rabbitmqClient;
    }

    @Scheduled(fixedRate = 90000)
    void sweepDirtyQueues(){
        try {
            var dirtyQueues = rabbitmqClient.getAllQueues().stream().filter(p -> p.isDirty())
                    .collect(Collectors.toList());

            dirtyQueues.forEach(queueName -> log.info("Queue {} has {} unprocessed messages", queueName.getName(), queueName.getMessages()));
        } catch (Exception e) {
            log.warn("Cannot sweep queues : " + e.getMessage());
        }
    }

}
