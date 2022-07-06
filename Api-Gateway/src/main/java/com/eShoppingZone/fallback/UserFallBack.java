package com.eShoppingZone.fallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eShoppingZone.model.Users;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserFallBack {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getUserFallBack")
	public Users getUserInfo(String customerId) {
		return restTemplate.getForObject("http://user-managment/user/" + customerId, Users.class);
	}

	public Users getUserFallBack(String customerId) {
		return new Users(customerId, "user not found", "");
	}
}
