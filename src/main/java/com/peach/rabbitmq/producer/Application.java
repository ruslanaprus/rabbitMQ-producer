package com.peach.rabbitmq.producer;

import com.peach.rabbitmq.producer.entity.Furniture;
import com.peach.rabbitmq.producer.producer.FurnitureProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private final FurnitureProducer producer;
	private final List<String> COLOURS = List.of("white", "red", "green");
	private final List<String> MATERIALS = List.of("wood", "plastic", "steel");

	public Application(FurnitureProducer producer) {
		this.producer = producer;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 10; i++) {
			var furniture = Furniture.builder()
					.name("furniture " + i)
					.colour(COLOURS.get(i % COLOURS.size()))
					.material(MATERIALS.get(i % MATERIALS.size()))
					.price(i)
					.build();
			producer.sendMessage(furniture);
		}
	}
}
