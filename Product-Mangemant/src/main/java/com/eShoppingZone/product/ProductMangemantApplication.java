package com.eShoppingZone.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.eShoppingZone.product.model.Product;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "Product API", description = "Product Management"))

public class ProductMangemantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductMangemantApplication.class, args);
	}

	@Bean
	public Object name() {
		return new Product(123, "Full Sleeves", "TS Athiletic", "T-Shirst", "abc.jpg", 499.0, 3.5,
				"Fully Conforatable");
	}

}
