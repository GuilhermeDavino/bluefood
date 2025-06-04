package com.blue.bluefood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.blue.bluefood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class BluefoodApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BluefoodApiApplication.class, args);
	}

}
