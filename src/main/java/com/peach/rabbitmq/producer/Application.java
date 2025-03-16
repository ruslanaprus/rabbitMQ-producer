package com.peach.rabbitmq.producer;

import com.peach.rabbitmq.producer.entity.Employee;
import com.peach.rabbitmq.producer.producer.EmployeeJsonProducer;
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

	private final EmployeeJsonProducer employeeJsonProducer;

	public Application(EmployeeJsonProducer employeeJsonProducer) {
		this.employeeJsonProducer = employeeJsonProducer;
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
			employeeJsonProducer.sendJsonMessage(employee);
		}
	}
}
