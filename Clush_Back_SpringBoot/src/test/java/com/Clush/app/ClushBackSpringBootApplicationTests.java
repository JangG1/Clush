package com.Clush.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
@Configuration
@EnableJpaRepositories(basePackages = "com.Clush.app.repository")
@EntityScan(basePackages = "com.Clush.app.model")
class ClushBackSpringBootApplicationTests {

	@Test
	void contextLoads() {
	}

}
