package com.pelec.start.first;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FirstApplication {
	public static DbInitializer client = new DbInitializer("localhost", "27017");

	@Bean
	public CommandLineRunner clr(ApplicationContext context) {
		return args -> {
			System.out.println("Dziaa");
		};
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FirstApplication.class);
		app.run();
	}

}
