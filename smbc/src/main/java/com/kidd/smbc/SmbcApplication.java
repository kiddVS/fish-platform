package com.kidd.smbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SmbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmbcApplication.class, args);
	}

}
