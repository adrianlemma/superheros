package com.challenge.w2m.superheros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class SuperherosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperherosApplication.class, args);
	}

}
