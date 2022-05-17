package com.eShoppingZone.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "Cart API", description = "Cart Management"))
public class CartMangemantApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartMangemantApplication.class, args);
	}

}
