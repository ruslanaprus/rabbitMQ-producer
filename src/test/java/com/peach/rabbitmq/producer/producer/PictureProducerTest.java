package com.peach.rabbitmq.producer.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.producer.entity.Picture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PictureProducerTest {

    private static final RabbitMQContainer RABBITMQ_CONTAINER =
            new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.12-management"));

    @BeforeAll
    static void startContainer() {
        RABBITMQ_CONTAINER.start();
        System.setProperty("RABBITMQ_PORT", String.valueOf(RABBITMQ_CONTAINER.getAmqpPort()));
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private RabbitAdmin rabbitAdmin;

    @BeforeEach
    void setup() {
        rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());
        // declare exchange
        DirectExchange exchange = new DirectExchange("x.picture");
        rabbitAdmin.declareExchange(exchange);

        // declare queues
        Queue imageQueue = new Queue("q.picture.image");
        Queue vectorQueue = new Queue("q.picture.vector");
        rabbitAdmin.declareQueue(imageQueue);
        rabbitAdmin.declareQueue(vectorQueue);

        // bind queues to exchange with routing keys
        rabbitAdmin.declareBinding(BindingBuilder.bind(imageQueue).to(exchange).with("jpg"));
        rabbitAdmin.declareBinding(BindingBuilder.bind(vectorQueue).to(exchange).with("svg"));

        // set JSON converter
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        // purge the queues before each test to ensure they are empty
        purgeQueues(imageQueue.getName(), vectorQueue.getName());
    }

    @Test
    void testSendJsonMessageToImageQueue() {
        // arrange
        Picture picture = new Picture("image1", "jpg", "web", 133L);
        PictureProducer producer = new PictureProducer(rabbitTemplate, objectMapper);

        // act
        producer.sendJsonMessage(picture);

        // assert
        Object received = rabbitTemplate.receiveAndConvert("q.picture.image");
        assertThat(received).isNotNull().isInstanceOf(String.class);
        System.out.println("Received Message in image queue: " + received);
    }

    @Test
    void testSendJsonMessageToVectorQueue() {
        // arrange
        Picture picture = new Picture("vector1", "svg", "web", 150L);
        PictureProducer producer = new PictureProducer(rabbitTemplate, objectMapper);

        // act
        producer.sendJsonMessage(picture);

        // assert
        Object received = rabbitTemplate.receiveAndConvert("q.picture.vector");
        assertThat(received).isNotNull().isInstanceOf(String.class);
        System.out.println("Received Message in vector queue: " + received);
    }

    @Test
    void testInvalidRoutingKeyDoesNotSendToQueues() {
        // arrange
        Picture picture = new Picture("unknown", "unknown", "web", 180L);
        PictureProducer producer = new PictureProducer(rabbitTemplate, objectMapper);

        // act
        producer.sendJsonMessage(picture);

        // assert
        Object receivedImage = rabbitTemplate.receiveAndConvert("q.picture.image");
        Object receivedVector = rabbitTemplate.receiveAndConvert("q.picture.vector");

        assertThat(receivedImage).isNull();
        assertThat(receivedVector).isNull();
    }

    private void purgeQueues(String... queueNames) {
        for (String queueName : queueNames) {
            rabbitAdmin.purgeQueue(queueName, false); // 'false' means don't remove consumers
        }
    }
}