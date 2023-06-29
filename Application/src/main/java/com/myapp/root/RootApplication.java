package com.myapp.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.myapp.root.repositories.PfCharacterRepository;

@SpringBootApplication
@EnableMongoRepositories
public class RootApplication {
    @Autowired
    PfCharacterRepository pfCharacterRepository;

	public static void main(String[] args) {
		SpringApplication.run(RootApplication.class, args);
	}

}
