package com.faltasproject;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.faltasproject.utils.InitialDataBase;

@SpringBootApplication
public class FaltasprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaltasprojectApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(InitialDataBase initialDataBase) {
		return args->initialDataBase.populateInitialDataIfNotExist();
	}

}
