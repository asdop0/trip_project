package com.asd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.asd")
public class SwAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwAuthApplication.class, args);
	}

}
