package com.eShoppingZone.cart.fallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eShoppingZone.cart.model.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ProductFallBack {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getProductFallBack")
	public Product getProduct(String productId) {
		return restTemplate.getForObject("http://product-managment/user/getById/" + productId, Product.class);
	}

	public Product getProductFallBack(String customerId) {
		return new Product(customerId, "Product not Found", 0);
	}

}
