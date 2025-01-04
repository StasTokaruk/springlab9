package com.labrob9.springlab9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Springlab9Application {

	public static void main(String[] args) {
		SpringApplication.run(Springlab9Application.class, args);
	}

}
