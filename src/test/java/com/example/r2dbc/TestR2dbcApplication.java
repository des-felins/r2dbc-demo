package com.example.r2dbc;

import org.springframework.boot.SpringApplication;

public class TestR2dbcApplication {

	public static void main(String[] args) {
		SpringApplication.from(R2dbcApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
