package com.eresh.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoAuditing
@EnableMongoRepositories
public class SpringMongoBulkInsertApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoBulkInsertApplication.class, args);
	}

}
