package com.SpringCoreConcepts.SprintCoreConcepts;

import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprintCoreConceptsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SprintCoreConceptsApplication.class, args);
	}
	DataBase postgres=new PostgresDB();
	DataBase mysql=new MySQLDB();
	UserService userService=new UserService(mysql);
	@Override
	public void run(String... args) throws Exception {
		userService.saveUser("Aashish");
	}
}
