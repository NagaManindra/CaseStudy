package com.eShoppingZone.order.fallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eShoppingZone.order.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserFallBack {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getUserFallBack")
	public User getUserInfo(String customerId) {
		return restTemplate.getForObject("http://user-managment/user/" + customerId, User.class);

	}

	public User getUserFallBack(String customerId) {
		return new User(customerId, "Name not found", null, null, null, null, 0, null, null);
	}
}