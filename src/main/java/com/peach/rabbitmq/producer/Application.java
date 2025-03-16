package com.peach.rabbitmq.producer;

import com.peach.rabbitmq.producer.entity.Employee;
import com.peach.rabbitmq.producer.producer.HumanResourceProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

//@EnableScheduling
@SpringBootApplication
public class Application implements CommandLineRunner {

	private final HumanResourceProducer producer;

	public Application(HumanResourceProducer producer) {
		this.producer = producer;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 5; i++) {
			var employee = Employee.builder()
					.id("emp-" + i)
					.name("Employee " + i)
					.birthday(LocalDate.now().minusYears(i))
					.build();
			producer.sendJsonMessage(employee);
		}
	}
}
