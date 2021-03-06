package com.eShoppingZone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eShoppingZone.model.Users;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SecurityController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping(value = "/user/{username}")
	// User get by username
	public Users getByUsername(@PathVariable("username") String username) {

		return restTemplate.getForObject("http://localhost:9007/user/" + username, Users.class);

	}

}
