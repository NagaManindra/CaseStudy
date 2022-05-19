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

@RestController
@RequestMapping("/web")
public class WebController {

	@Autowired
	private RestTemplate restTemplate;

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

	// User get by username
	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public User getByUsername(@PathVariable("username") String username) {

		ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:9001/user/" + username, User.class);
		return response.getBody();

	}

	// User register
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<User> userRegister(@RequestBody User user) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);
		return restTemplate.exchange("http://localhost:9001/user/register", HttpMethod.POST, entity, User.class);

	}

	// User update
	@RequestMapping(value = "/user/update/{username}", method = RequestMethod.PUT)
	public ResponseEntity<User> userUpdate(@PathVariable("username") String username, @RequestBody User user) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);
		return restTemplate.exchange("http://localhost:9001/user/update/" + username, HttpMethod.PUT, entity,
				User.class);

	}

	// User delete
	@RequestMapping(value = "/user/delete/{username}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable("username") String username) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(headers);
		return restTemplate
				.exchange("http://localhost:9001/user/delete/" + username, HttpMethod.DELETE, entity, String.class)
				.getBody();
	}

	// Cart get cart
	@RequestMapping(value = "/getcart/{cartId}", method = RequestMethod.GET)
	public ResponseEntity<Cart> getCart(@PathVariable("cartId") String cartId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cart> entity = new HttpEntity<Cart>(headers);
		return restTemplate.exchange("http://localhost:9003/cart/getcart/" + cartId, HttpMethod.GET, entity,
				Cart.class);

	}

	// Cart add item
	@RequestMapping(value = "/additem/{cartId}/{productId}", method = RequestMethod.POST)
	public ResponseEntity<Cart> addCart(@RequestBody Item item, @PathVariable("cartId") String cartId,
			@PathVariable("productId") String productId) {
		CartProduct product = restTemplate.getForObject("http://localhost:9002/product/admin/getById/" + productId,
				CartProduct.class);
		if (product != null) {
			item.setProduct(product);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Item> entity = new HttpEntity<Item>(item, headers);
			return restTemplate.exchange("http://localhost:9003/cart/additem/" + cartId, HttpMethod.POST, entity,
					Cart.class);
		}
		return null;

	}

	// Cart delete item
	@RequestMapping(value = "/deleteItem/{cartId}/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<Cart> deleteCartItem(@PathVariable("cartId") String cartId,
			@PathVariable("productId") String productId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cart> entity = new HttpEntity<Cart>(headers);
		return restTemplate.exchange("http://localhost:9003/cart/deleteitem/" + cartId + "/" + productId,
				HttpMethod.DELETE, entity, Cart.class);

	}

	// Cart delete all
	@RequestMapping(value = "/deleteCart/{cartId}", method = RequestMethod.DELETE)
	public ResponseEntity<Cart> deleteCart(@PathVariable("cartId") String cartId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cart> entity = new HttpEntity<Cart>(headers);
		return restTemplate.exchange("http://localhost:9003/cart/deletecart/" + cartId, HttpMethod.DELETE, entity,
				Cart.class);

	}

	// Cart update
	@RequestMapping(value = "/updateitem/{cartId}/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<Cart> updateCart(@PathVariable("cartId") String cartId, @RequestBody Item item,
			@PathVariable("productId") String productId) {
		CartProduct product = restTemplate.getForObject("http://localhost:9002/product/admin/getById/" + productId,
				CartProduct.class);
		if (product != null) {
			item.setProduct(product);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Item> entity = new HttpEntity<Item>(item, headers);
			return restTemplate.exchange("http://localhost:9003/cart/updateitem/" + cartId, HttpMethod.PUT, entity,
					Cart.class);
		}
		return null;
	}

	// Order create
	@RequestMapping(value = "/addOrder/{customerId}", method = RequestMethod.POST)
	public ResponseEntity<Order> addOrder(@PathVariable("customerId") String customerId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Order> entity = new HttpEntity<Order>(headers);
		return restTemplate.exchange("http://localhost:9004/order/addOrder/" + customerId, HttpMethod.POST, entity,
				Order.class);

	}

	// Order get all
	@RequestMapping(value = "/getOrder/{customerId}", method = RequestMethod.GET)
	public Order[] getOrder(@PathVariable("customerId") String customerId) {
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON);
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 * HttpEntity<Order> entity = new HttpEntity<Order>(headers); return
		 * restTemplate.exchange("http://localhost:9004/order/getOrder/" + customerId,
		 * HttpMethod.GET, entity, Order.class);
		 */
		ResponseEntity<Order[]> response = restTemplate
				.getForEntity("http://localhost:9004/order/getOrder/" + customerId, Order[].class);
		return response.getBody();

	}

	// order get by current date
	@RequestMapping(value = "/getTodaysOrders/{customerId}", method = RequestMethod.GET)
	public Order[] getOrderByDate(@PathVariable("customerId") String customerId) {

		ResponseEntity<Order[]> response = restTemplate
				.getForEntity("http://localhost:9004/order/getTodaysOrders/" + customerId, Order[].class);
		return response.getBody();

	}

	// order get by customerId
	@RequestMapping(value = "/getByOrderId/{orderId}", method = RequestMethod.GET)
	public Order[] getByOrderId(@PathVariable("orderId") String orderId) {

		ResponseEntity<Order[]> response = restTemplate
				.getForEntity("http://localhost:9004/order/getByOrderId/" + orderId, Order[].class);
		return response.getBody();

	}

	// order delete by customerId
	@RequestMapping(value = "/deleteOrder/{orderId}", method = RequestMethod.DELETE)
	public String deleteOrder(@PathVariable("orderId") String orderId) {

		restTemplate.exchange("http://localhost:9004/order/deleteOrder/" + orderId, HttpMethod.DELETE, null,
				String.class);

		return orderId + " deleted";
	}

	@RequestMapping(value = "/wallet")
	public String wallet() {

		restTemplate.exchange("http://localhost:9005/wallet/", HttpMethod.POST, null, String.class);

		return "deleted";
	}

}
