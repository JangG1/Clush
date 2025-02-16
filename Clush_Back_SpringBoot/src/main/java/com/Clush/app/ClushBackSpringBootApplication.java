package com.Clush.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.Clush.app.Repository")
public class ClushBackSpringBootApplication {

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(ClushBackSpringBootApplication.class, args);		
        InetAddress ipAddress = InetAddress.getLocalHost();

        System.out.println("현재 아이피 : " + ipAddress.getHostAddress());
        System.out.println("현재 호스트명 : " + ipAddress.getHostName());
	}

}
