package com.peach.rabbitmq.producer.client;

import com.peach.rabbitmq.producer.entity.RabbitmqQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Base64;
import java.util.List;

@Service
public class RabbitmqClient {

    @Value("${rabbitmq.management.host}")
    private String rabbitmqHost;

    @Value("${rabbitmq.management.port}")
    private String rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUser;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;


    public List<RabbitmqQueue> getAllQueues(){
        String baseUrl = String.format("http://%s:%s/api/queues", rabbitmqHost, rabbitmqPort);
        WebClient webClient = WebClient.create(baseUrl);

        return webClient.get()
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeaders())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RabbitmqQueue>>() {})
                .block(Duration.ofSeconds(10));
    }

    String createBasicAuthHeaders(){
        String auth = rabbitmqUser + ":" + rabbitmqPassword;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
