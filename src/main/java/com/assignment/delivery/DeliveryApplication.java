package com.assignment.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.assignment.delivery.repository",
		"com.assignment.delivery.controller",
		"com.assignment.delivery.handler",
		"com.assignment.delivery.config"
})
@EnableAutoConfiguration
public class DeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryApplication.class, args);
	}

}
