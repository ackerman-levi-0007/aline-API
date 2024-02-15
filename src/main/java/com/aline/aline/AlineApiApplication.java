package com.aline.aline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class AlineApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlineApiApplication.class, args);
	}

}
