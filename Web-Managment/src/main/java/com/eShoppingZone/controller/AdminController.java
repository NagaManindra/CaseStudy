package com.eShoppingZone.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eShoppingZone.model.Order;
import com.eShoppingZone.model.Product;
import com.eShoppingZone.model.User;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private RestTemplate restTemplate;

	// User get all users
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public User[] getAllUsers() {

		ResponseEntity<User[]> response = restTemplate.getForEntity("http://localhost:9001/user/getAll", User[].class);
		return response.getBody();

	}

	// Product get by name

	@RequestMapping(value = "/findByProductName/{productName}", method = RequestMethod.GET)
	public Product[] getAllProducts(@PathVariable("productName") String productName) {
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON);
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 * HttpEntity<Product> entity = new HttpEntity<Product>(headers);
		 */
		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://localhost:9002/product/user/getByName/" + productName, Product[].class);
		return response.getBody();

	}

	// Product get all
	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	public Product[] getProduct() {

		ResponseEntity<Product[]> response = restTemplate.getForEntity("http://localhost:9002/product/user/getAll",
				Product[].class);

		return response.getBody();
	}

	// Product get by category
	@RequestMapping(value = "/getByCategory/{category}", method = RequestMethod.GET)
	public Product[] getBycategory(@PathVariable("category") String category) {

		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://localhost:9002/product/user/getByCategory/" + category, Product[].class);
		return response.getBody();

	}

	// Product get by type
	@RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
	public Product[] getByType(@PathVariable("type") String type) {

		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://localhost:9002/product/user/getByType/" + type, Product[].class);
		return response.getBody();

	}

	// Product add
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
		return restTemplate.exchange("http://localhost:9002/product/admin/addProduct", HttpMethod.POST, entity,
				Product.class);
	}

	// Product edit product
	@RequestMapping(value = "/update/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(@PathVariable("productId") String productId,
			@RequestBody Product product) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
		return restTemplate.exchange("http://localhost:9002/product/admin/update/" + productId, HttpMethod.PUT, entity,
				Product.class);
	}

	// Product delete
	@RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<Product> deleteProduct(@PathVariable("productId") String productId,
			@RequestBody Product product) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
		return restTemplate.exchange("http://localhost:9002/product/admin/deleteproduct/" + productId,
				HttpMethod.DELETE, entity, Product.class);
	}

	// Order update
	@RequestMapping(value = "/updateOrder/{orderId}", method = RequestMethod.PUT)
	public Order getByOrderId(@PathVariable("orderId") String orderId) {

		ResponseEntity<Order> response = restTemplate
				.getForEntity("http://localhost:9004/order/getByOrderId/" + orderId, Order.class);
		return response.getBody();

	}
}
