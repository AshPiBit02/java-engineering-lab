package com.SpringCoreConcepts.SprintCoreConcepts;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprintCoreConceptsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SprintCoreConceptsApplication.class, args);
	}

	private final UserService userService;
	public SprintCoreConceptsApplication(UserService userService)
	{
		this.userService =userService;
	}

	@Override
	public void run(String... args) throws Exception {
		userService.saveUser("Aashish");
	}
}
