package com.Security.Authify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class AuthifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthifyApplication.class, args);
	}

}
