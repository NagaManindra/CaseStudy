package com.eShoppingZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigMangemantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigMangemantApplication.class, args);
	}

}
