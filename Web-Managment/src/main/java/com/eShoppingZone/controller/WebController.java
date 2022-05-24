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

import com.eShoppingZone.model.Cart;
import com.eShoppingZone.model.CartProduct;
import com.eShoppingZone.model.Item;
import com.eShoppingZone.model.Order;
import com.eShoppingZone.model.Product;
import com.eShoppingZone.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/web")
public class WebController {

	@Autowired
	private RestTemplate restTemplate;

	// Product get by name

	@Operation(summary = "Find Product by its Name")
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
	@Operation(summary = "To Display All the Product")
	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	public Product[] getProduct() {

		ResponseEntity<Product[]> response = restTemplate.getForEntity("http://product-managment/product/user/getAll",
				Product[].class);

		return response.getBody();
	}

	// Product get by category
	@Operation(summary = "Get Product by Category")
	@RequestMapping(value = "/getByCategory/{category}", method = RequestMethod.GET)
	public Product[] getBycategory(
			@Parameter(description = "Enter Product Category") @PathVariable("category") String category) {

		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://product-managment/product/user/getByCategory/" + category, Product[].class);
		return response.getBody();

	}

	// Product get by type
	@Operation(summary = "Get Product by Type")
	@RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
	public Product[] getByType(@Parameter(description = "Enter Product Type") @PathVariable("type") String type) {

		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://product-managment/product/user/getByType/" + type, Product[].class);
		return response.getBody();

	}

	// User get by username
	@Operation(summary = "Get User by userName")
	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public User getByUsername(@Parameter(description = "Enter User Name") @PathVariable("username") String username) {

		ResponseEntity<User> response = restTemplate.getForEntity("http://user-managment/user/" + username, User.class);
		return response.getBody();

	}

	// User register
	@Operation(summary = "Add new User")
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<User> userRegister(@Parameter(description = "Enter User Details") @RequestBody User user) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);
		return restTemplate.exchange("http://user-managment/user/register", HttpMethod.POST, entity, User.class);

	}

	// User update
	@Operation(summary = "Update User Details")
	@RequestMapping(value = "/user/update/{username}", method = RequestMethod.PUT)
	public ResponseEntity<User> userUpdate(
			@Parameter(description = "Enter User Name") @PathVariable("username") String username,
			@Parameter(description = "Enter Update Details") @RequestBody User user) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);
		return restTemplate.exchange("http://user-managment/user/update/" + username, HttpMethod.PUT, entity,
				User.class);

	}

	// User delete
	@Operation(summary = "Delete User")
	@RequestMapping(value = "/user/delete/{username}", method = RequestMethod.DELETE)
	public String deleteUser(@Parameter(description = "Enter userName") @PathVariable("username") String username) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(headers);
		return restTemplate
				.exchange("http://user-managment/user/delete/" + username, HttpMethod.DELETE, entity, String.class)
				.getBody();
	}

	// Cart get cart
	@Operation(summary = "Get Cart by cartId")
	@RequestMapping(value = "/getcart/{cartId}", method = RequestMethod.GET)
	public ResponseEntity<Cart> getCart(
			@Parameter(description = "Enter Cart Id") @PathVariable("cartId") String cartId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cart> entity = new HttpEntity<Cart>(headers);
		return restTemplate.exchange("http://cart-managment/cart/getcart/" + cartId, HttpMethod.GET, entity,
				Cart.class);

	}

	// Cart add item
	@Operation(summary = "Add Items to Cart")
	@RequestMapping(value = "/additem/{cartId}/{productId}", method = RequestMethod.POST)
	public ResponseEntity<Cart> addCart(@Parameter(description = "Enter Quantity") @RequestBody Item item,
			@Parameter(description = "Enter Cart Id") @PathVariable("cartId") String cartId,
			@Parameter(description = "Enter product Id") @PathVariable("productId") String productId) {
		CartProduct product = restTemplate.getForObject("http://product-managment/product/admin/getById/" + productId,
				CartProduct.class);
		if (product != null) {
			item.setProduct(product);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Item> entity = new HttpEntity<Item>(item, headers);
			return restTemplate.exchange("http://cart-managment/cart/additem/" + cartId, HttpMethod.POST, entity,
					Cart.class);
		}
		return null;

	}

	// Cart delete item
	@Operation(summary = "Delete Items in Cart")
	@RequestMapping(value = "/deleteItem/{cartId}/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<Cart> deleteCartItem(
			@Parameter(description = "Enter Cart Id") @PathVariable("cartId") String cartId,
			@Parameter(description = "Enter Product Id") @PathVariable("productId") String productId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cart> entity = new HttpEntity<Cart>(headers);
		return restTemplate.exchange("http://cart-managment/cart/deleteitem/" + cartId + "/" + productId,
				HttpMethod.DELETE, entity, Cart.class);

	}

	// Cart delete all
	@Operation(summary = "Delete All Items in Cart")
	@RequestMapping(value = "/deleteCart/{cartId}", method = RequestMethod.DELETE)
	public ResponseEntity<Cart> deleteCart(
			@Parameter(description = "Enter Cart Id") @PathVariable("cartId") String cartId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cart> entity = new HttpEntity<Cart>(headers);
		return restTemplate.exchange("http://cart-managment/cart/deletecart/" + cartId, HttpMethod.DELETE, entity,
				Cart.class);

	}

	// Cart update
	@Operation(summary = "Update Items in Cart")
	@RequestMapping(value = "/updateitem/{cartId}/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<Cart> updateCart(
			@Parameter(description = "Enter Cart Id") @PathVariable("cartId") String cartId,
			@Parameter(description = "Enter Quantity") @RequestBody Item item,
			@Parameter(description = "Enter Product Id") @PathVariable("productId") String productId) {
		CartProduct product = restTemplate.getForObject("http://product-managment/product/admin/getById/" + productId,
				CartProduct.class);
		if (product != null) {
			item.setProduct(product);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Item> entity = new HttpEntity<Item>(item, headers);
			return restTemplate.exchange("http://cart-managment/cart/updateitem/" + cartId, HttpMethod.PUT, entity,
					Cart.class);
		}
		return null;
	}

	// Order create
	@Operation(summary = "To place Order")
	@RequestMapping(value = "/addOrder/{customerId}", method = RequestMethod.POST)
	public ResponseEntity<Order> addOrder(
			@Parameter(description = "Enter Customer Id") @PathVariable("customerId") String customerId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Order> entity = new HttpEntity<Order>(headers);
		return restTemplate.exchange("http://order-managment/order/addOrder/" + customerId, HttpMethod.POST, entity,
				Order.class);

	}

	// Order get all
	@Operation(summary = "To get All customer Orders")
	@RequestMapping(value = "/getOrder/{customerId}", method = RequestMethod.GET)
	public Order[] getOrder(
			@Parameter(description = "Enter Customer Id") @PathVariable("customerId") String customerId) {
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON);
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 * HttpEntity<Order> entity = new HttpEntity<Order>(headers); return
		 * restTemplate.exchange("http://localhost:9004/order/getOrder/" + customerId,
		 * HttpMethod.GET, entity, Order.class);
		 */
		ResponseEntity<Order[]> response = restTemplate
				.getForEntity("http://order-managment/order/getOrder/" + customerId, Order[].class);
		return response.getBody();

	}

	// order get by current date
	@Operation(summary = "To get Oder by Current Date")
	@RequestMapping(value = "/getTodaysOrders/{customerId}", method = RequestMethod.GET)
	public Order[] getOrderByDate(
			@Parameter(description = "Enter Customer Id") @PathVariable("customerId") String customerId) {

		ResponseEntity<Order[]> response = restTemplate
				.getForEntity("http://order-managment/order/getTodaysOrders/" + customerId, Order[].class);
		return response.getBody();

	}

	// order get by customerId
	@Operation(summary = "To get customer Orders")
	@RequestMapping(value = "/getByOrderId/{orderId}", method = RequestMethod.GET)
	public Order[] getByOrderId(@Parameter(description = "Enter Order Id") @PathVariable("orderId") String orderId) {

		ResponseEntity<Order[]> response = restTemplate
				.getForEntity("http://order-managment/order/getByOrderId/" + orderId, Order[].class);
		return response.getBody();

	}

	// order delete by customerId
	@Operation(summary = "Delete Orders by CustomerId")
	@RequestMapping(value = "/deleteOrder/{orderId}", method = RequestMethod.DELETE)
	public String deleteOrder(@Parameter(description = "Enter Order Id") @PathVariable("orderId") String orderId) {

		restTemplate.exchange("http://order-managment/order/deleteOrder/" + orderId, HttpMethod.DELETE, null,
				String.class);

		return orderId + " deleted";
	}

	@RequestMapping(value = "/wallet")
	public String wallet() {

		restTemplate.exchange("http://localhost:9005/wallet/", HttpMethod.POST, null, String.class);

		return "deleted";
	}

}
