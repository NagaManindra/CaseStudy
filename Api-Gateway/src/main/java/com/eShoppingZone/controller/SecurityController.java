package com.eShoppingZone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eShoppingZone.model.Users;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SecurityController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	// User get by username
	public Users getByUsername(@PathVariable("username") String username) {

		Users response = restTemplate.getForObject("http://localhost:9007/user/" + username, Users.class);
		return response;

	}

}
