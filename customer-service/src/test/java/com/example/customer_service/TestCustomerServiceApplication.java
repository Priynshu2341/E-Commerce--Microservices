package com.example.customer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;


public class TestCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(CustomerServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
