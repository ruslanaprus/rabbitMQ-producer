package com.peach.rabbitmq.producer;

import com.peach.rabbitmq.producer.entity.Employee;
import com.peach.rabbitmq.producer.entity.Picture;
import com.peach.rabbitmq.producer.producer.HumanResourceProducer;
import com.peach.rabbitmq.producer.producer.PictureProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//@EnableScheduling
@SpringBootApplication
public class Application implements CommandLineRunner {

	private final PictureProducer producer;
	private final List<String> SOURCES = List.of("mobile", "email", "web");
	private final List<String> TYPES = List.of("jpg", "png", "svg");

	public Application(PictureProducer producer) {
		this.producer = producer;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 10; i++) {
			var picture = Picture.builder()
					.name("pic-" + i)
					.source(SOURCES.get(i % SOURCES.size()))
					.type(TYPES.get(i % TYPES.size()))
					.size(ThreadLocalRandom.current().nextLong(1, 10000))
					.build();
			producer.sendJsonMessage(picture);
		}
	}
}
