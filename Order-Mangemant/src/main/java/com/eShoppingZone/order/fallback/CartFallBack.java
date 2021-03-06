package com.eShoppingZone.order.fallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eShoppingZone.order.model.Cart;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class CartFallBack {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getCartFallBack")
	public Cart getCart(String customerId) {
		return restTemplate.getForObject("http://cart-managment/user/getcart/" + customerId, Cart.class);
	}

	public Cart getCartFallBack(String customerId) {
		return new Cart(customerId, 0, null);
	}

}
