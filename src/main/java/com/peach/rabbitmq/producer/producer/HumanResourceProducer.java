package com.peach.rabbitmq.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.producer.entity.Employee;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class HumanResourceProducer {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    public HumanResourceProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendJsonMessage(Employee employee) {
        try {
            var json = objectMapper.writeValueAsString(employee);
            rabbitTemplate.convertAndSend("x.hr", "", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
