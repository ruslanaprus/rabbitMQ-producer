package com.peach.rabbitmq.producer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private final RabbitProducer rabbitProducer;

	public Application(RabbitProducer rabbitProducer) {
		this.rabbitProducer = rabbitProducer;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		rabbitProducer.sendHello("Peach " + ThreadLocalRandom.current().nextInt());
	}
}
