package com.peach.rabbitmq.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.producer.entity.Furniture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class FurnitureTopicProducer {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    public FurnitureTopicProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendJsonMessage(Furniture furniture) {
        try {
            String json = objectMapper.writeValueAsString(furniture);
            
            // Create a routing key based on furniture attributes: colour.material.price-category
            StringBuilder routingKey = new StringBuilder();
            
            // First part is colour (all lowercase)
            routingKey.append(furniture.getColour().toLowerCase());
            routingKey.append(".");
            
            // Second part is material (all lowercase)
            routingKey.append(furniture.getMaterial().toLowerCase());
            routingKey.append(".");
            
            // Third part is price category
            if (furniture.getPrice() >= 100) {
                routingKey.append("expensive");
            } else {
                routingKey.append("budget");
            }
            
            // Send to topic exchange with our constructed routing key
            rabbitTemplate.convertAndSend("x.furniture.topic", routingKey.toString(), json);
            
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}