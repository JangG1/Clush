package com.Clush.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.Clush.app.Repository")
public class ClushBackSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClushBackSpringBootApplication.class, args);
	}

}
