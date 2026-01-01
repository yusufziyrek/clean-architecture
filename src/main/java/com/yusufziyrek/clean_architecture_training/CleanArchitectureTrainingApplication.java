package com.yusufziyrek.clean_architecture_training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CleanArchitectureTrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CleanArchitectureTrainingApplication.class, args);
	}

}
