package com.peach.rabbitmq.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.producer.entity.Picture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyPictureProducer {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    public MyPictureProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendJsonMessage(Picture picture) {
        try {
            var json = objectMapper.writeValueAsString(picture);
            rabbitTemplate.convertAndSend("x.mypicture", picture.getType(), json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
