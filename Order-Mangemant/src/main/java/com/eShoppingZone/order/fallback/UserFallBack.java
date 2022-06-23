package com.eShoppingZone.order.fallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eShoppingZone.order.model.Address;
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
		Address address = new Address(0, customerId, customerId, customerId, customerId, 0);
		return new User(customerId, "", "", "", "", "", 0, "", address);
	}
}
