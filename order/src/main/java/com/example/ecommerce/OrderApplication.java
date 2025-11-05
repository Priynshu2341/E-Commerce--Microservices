package com.example.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.ecommerce.feignclient")
@EnableJpaAuditing
public class OrderApplication {

	public static void main(String[] args) {
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("Asia/Kolkata"));
        System.out.println(">>> JVM timezone: " + java.util.TimeZone.getDefault().getID());
        SpringApplication.run(OrderApplication.class, args);
	}

}
