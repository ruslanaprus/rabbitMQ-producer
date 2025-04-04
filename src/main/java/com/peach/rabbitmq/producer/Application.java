package com.peach.rabbitmq.producer;

import com.peach.rabbitmq.producer.entity.Picture;
import com.peach.rabbitmq.producer.producer.RetryPictureProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@EnableScheduling
@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {}

//	private final RetryPictureProducer producer;
//	private final List<String> SOURCES = List.of("mobile", "web");
//	private final List<String> TYPES = List.of("jpg", "png", "svg");
//
//	public Application(RetryPictureProducer producer) {
//		this.producer = producer;
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		for (int i = 0; i < 10; i++) {
//			var picture = Picture.builder()
//					.name("picture " + i)
//					.size(ThreadLocalRandom.current().nextLong(9001, 10000))
//					.source(SOURCES.get(i % SOURCES.size()))
//					.type(TYPES.get(i % TYPES.size()))
//					.build();
//			producer.sendJsonMessage(picture);
//		}
//	}

//	private final FurnitureProducer producer;
//	private final List<String> COLOURS = List.of("white", "red", "green");
//	private final List<String> MATERIALS = List.of("wood", "plastic", "steel");
//
//	public Application(FurnitureProducer producer) {
//		this.producer = producer;
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		for (int i = 0; i < 10; i++) {
//			var furniture = Furniture.builder()
//					.name("furniture " + i)
//					.colour(COLOURS.get(i % COLOURS.size()))
//					.material(MATERIALS.get(i % MATERIALS.size()))
//					.price(i)
//					.build();
//			producer.sendMessage(furniture);
//		}
//	}
}
