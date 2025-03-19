package com.peach.rabbitmq.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.producer.entity.Furniture;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class FurnitureProducer {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    public FurnitureProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Furniture furniture) {
        var messageProperties = new MessageProperties();

        messageProperties.setHeader("colour", furniture.getColour());
        messageProperties.setHeader("material", furniture.getMaterial());

        try {
            var json = objectMapper.writeValueAsString(furniture);
            var message = new Message(json.getBytes(), messageProperties);

            rabbitTemplate.send("x.promotion", "", message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
