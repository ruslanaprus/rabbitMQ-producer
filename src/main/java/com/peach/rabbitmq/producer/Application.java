package com.peach.rabbitmq.producer;

import com.peach.rabbitmq.producer.entity.Picture;
import com.peach.rabbitmq.producer.producer.PictureProducerTopic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//@EnableScheduling
@SpringBootApplication
public class Application implements CommandLineRunner {

	private final PictureProducerTopic producer;
	private final List<String> SOURCES = List.of("mobile", "email", "web");
	private final List<String> TYPES = List.of("jpg", "png", "svg");

	public Application(PictureProducerTopic producer) {
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
