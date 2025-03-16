package com.peach.rabbitmq.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.producer.entity.Employee;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeJsonProducer {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    public EmployeeJsonProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendJsonMessage(Employee employee) {
        try {
            var json = objectMapper.writeValueAsString(employee);
            rabbitTemplate.convertAndSend("peach.employee", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
