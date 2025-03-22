package com.peach.rabbitmq.producer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean
    ObjectMapper objectMapper() {
        return JsonMapper.builder().findAndAddModules().build();
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
    
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
    
    // Define exchanges
    @Bean
    public DirectExchange guidelineExchange() {
        return new DirectExchange("x.guideline.work");
    }
    
    @Bean
    public DirectExchange pictureExchange() {
        return new DirectExchange("x.picture");
    }
    
    @Bean
    public TopicExchange picture2Exchange() {
        return new TopicExchange("x.picture2");
    }
    
    @Bean
    public DirectExchange myPictureExchange() {
        return new DirectExchange("x.mypicture");
    }
    
    @Bean
    public DirectExchange hrExchange() {
        return new DirectExchange("x.hr");
    }
    
    @Bean
    public DirectExchange promotionExchange() {
        return new DirectExchange("x.promotion");
    }
    
    // Define queues
    @Bean
    public Queue pictureImageQueue() {
        return new Queue("q.picture.image");
    }
    
    @Bean
    public Queue pictureVectorQueue() {
        return new Queue("q.picture.vector");
    }
    
    @Bean
    public Queue guidelineQueue() {
        return new Queue("q.guideline");
    }
    
    // Simple queues for other producers
    @Bean
    public Queue helloQueue() {
        return new Queue("peach.hello");
    }
    
    @Bean
    public Queue fixedRateQueue() {
        return new Queue("peach.fixrate");
    }
    
    @Bean
    public Queue employeeQueue() {
        return new Queue("peach.employee");
    }
    
    // Define bindings
    @Bean
    public Binding imageBinding(Queue pictureImageQueue, DirectExchange pictureExchange) {
        return BindingBuilder.bind(pictureImageQueue)
                .to(pictureExchange)
                .with("jpg");
    }
    
    @Bean
    public Binding vectorBinding(Queue pictureVectorQueue, DirectExchange pictureExchange) {
        return BindingBuilder.bind(pictureVectorQueue)
                .to(pictureExchange)
                .with("svg");
    }
    
    @Bean
    public Binding guidelineJpgBinding(Queue guidelineQueue, DirectExchange guidelineExchange) {
        return BindingBuilder.bind(guidelineQueue)
                .to(guidelineExchange)
                .with("jpg");
    }
    
    @Bean
    public Binding guidelinePngBinding(Queue guidelineQueue, DirectExchange guidelineExchange) {
        return BindingBuilder.bind(guidelineQueue)
                .to(guidelineExchange)
                .with("png");
    }
    
    @Bean
    public Binding guidelineSvgBinding(Queue guidelineQueue, DirectExchange guidelineExchange) {
        return BindingBuilder.bind(guidelineQueue)
                .to(guidelineExchange)
                .with("svg");
    }
}
