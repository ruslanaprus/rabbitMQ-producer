package com.peach.rabbitmq.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.producer.entity.Picture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PictureProducerTopic {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    public PictureProducerTopic(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendJsonMessage(Picture picture) {
        try {
            var json = objectMapper.writeValueAsString(picture);
            var sb = new StringBuilder();

            // 1st word is "mobile" or "web" - picture source
            sb.append(picture.getSource());
            sb.append(".");

            // 2nd word is "small" or "large" based on picture size
            if (picture.getSize() > 4000){
                sb.append("large");
            } else {
                sb.append("small");
            }
            sb.append(".");

            // 3rd word is picture type
            sb.append(picture.getType());

            var routingKey = sb.toString();
            rabbitTemplate.convertAndSend("x.picture2", routingKey, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
