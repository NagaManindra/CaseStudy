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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private RestTemplate restTemplate;

	// User get all users
	@Operation(summary = "Get all registered Users")
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public User[] getAllUsers() {

		ResponseEntity<User[]> response = restTemplate.getForEntity("http://user-managment/user/getAll", User[].class);
		return response.getBody();

	}

	// Product get by name
	@Operation(summary = "Get User by userName")
	@RequestMapping(value = "/findByProductName/{productName}", method = RequestMethod.GET)
	public Product[] getAllProducts(
			@Parameter(description = "Enter Product Name") @PathVariable("productName") String productName) {
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON);
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 * HttpEntity<Product> entity = new HttpEntity<Product>(headers);
		 */
		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://product-managment/product/user/getByName/" + productName, Product[].class);
		return response.getBody();

	}

	// Product get all
	@Operation(summary = "To Display All Products")
	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	public Product[] getProduct() {

		ResponseEntity<Product[]> response = restTemplate.getForEntity("http://product-managment/product/user/getAll",
				Product[].class);

		return response.getBody();
	}

	// Product get by category
	@Operation(summary = "Get Products by Category")
	@RequestMapping(value = "/getByCategory/{category}", method = RequestMethod.GET)
	public Product[] getBycategory(
			@Parameter(description = "Enter Product Category") @PathVariable("category") String category) {

		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://product-managment/product/user/getByCategory/" + category, Product[].class);
		return response.getBody();

	}

	// Product get by type
	@Operation(summary = "Get Products by Type")
	@RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
	public Product[] getByType(@Parameter(description = "Enter Product Type") @PathVariable("type") String type) {

		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://product-managment/product/user/getByType/" + type, Product[].class);
		return response.getBody();

	}

	// Product add
	@Operation(summary = "Add Product")
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public ResponseEntity<Product> addProduct(
			@Parameter(description = "Enter Product Details") @RequestBody Product product) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
		return restTemplate.exchange("http://product-managment/product/admin/addProduct", HttpMethod.POST, entity,
				Product.class);
	}

	// Product edit product
	@Operation(summary = "Edit Product")
	@RequestMapping(value = "/update/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(
			@Parameter(description = "Enter Product Id") @PathVariable("productId") String productId,
			@Parameter(description = "Enter Update Details") @RequestBody Product product) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
		return restTemplate.exchange("http://product-managment/product/admin/update/" + productId, HttpMethod.PUT,
				entity, Product.class);
	}

	// Product delete
	@Operation(summary = "Delete Product")
	@RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<Product> deleteProduct(
			@Parameter(description = "Enter Product Id") @PathVariable("productId") String productId,
			@RequestBody Product product) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
		return restTemplate.exchange("http://product-managment/product/admin/deleteproduct/" + productId,
				HttpMethod.DELETE, entity, Product.class);
	}

	// Order update
	@Operation(summary = "Update Orders")
	@RequestMapping(value = "/updateOrder/{orderId}", method = RequestMethod.PUT)
	public Order getByOrderId(@Parameter(description = "Enter Order Id") @PathVariable("orderId") String orderId) {

		ResponseEntity<Order> response = restTemplate
				.getForEntity("http://order-managment/order/getByOrderId/" + orderId, Order.class);
		return response.getBody();

	}
}
