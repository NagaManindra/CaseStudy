package com.eShoppingZone.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.eShoppingZone.model.Cart;
import com.eShoppingZone.model.CartProduct;
import com.eShoppingZone.model.Item;
import com.eShoppingZone.model.Order;
import com.eShoppingZone.model.Product;
import com.eShoppingZone.model.Users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Controller
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
				.getForEntity("http://product-managment/product/user/getByName/" + productName, Product[].class);
		return response.getBody();

	}

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	@ResponseBody
	public String currentUserName(Authentication authentication) {
		return authentication.getName();
	}

	// Product get all
	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	public String getProduct(Model model, Authentication authentication) {

		ResponseEntity<Product[]> response = restTemplate.getForEntity("http://product-managment/product/user/getAll",
				Product[].class);
		model.addAttribute("list", response.getBody());
		model.addAttribute("details", authentication.getName());
		return "home";
	}

	// Product get by category
	@RequestMapping(value = "/getByCategory/{category}", method = RequestMethod.GET)
	public Product[] getBycategory(@PathVariable("category") String category) {

		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://product-managment/product/user/getByCategory/" + category, Product[].class);
		return response.getBody();

	}

	// Product get by type
	@RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
	public Product[] getByType(@PathVariable("type") String type) {

		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://product-managment/product/user/getByType/" + type, Product[].class);
		return response.getBody();

	}

	// User get by username
	public Users getByUsername(@PathVariable("username") String username) {

		Users response = restTemplate.getForObject("http://user-managment/user/" + username, Users.class);
		return response;

	}

	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public String getByUser(@PathVariable("username") String username, Model model, Authentication authentication) {

		Users response = restTemplate.getForObject("http://user-managment/user/" + username, Users.class);
		model.addAttribute("user", response);
		model.addAttribute("details", authentication.getName());
		return "userDetails";

	}

	// User register
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<Users> userRegister(@RequestBody Users user) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Users> entity = new HttpEntity<Users>(user, headers);
		return restTemplate.exchange("http://user-managment/user/register", HttpMethod.POST, entity, Users.class);

	}

	@RequestMapping("/update/{username}")
	public String update(@PathVariable("username") String username, Model model, Authentication authentication) {
		Users response = restTemplate.getForObject("http://user-managment/user/" + username, Users.class);
		model.addAttribute("user", response);
		model.addAttribute("details", authentication.getName());
		return "userUpdate";
	}

	// User delete
	@RequestMapping(value = "/user/delete/{username}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable("username") String username) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Users> entity = new HttpEntity<Users>(headers);
		return restTemplate
				.exchange("http://user-managment/user/delete/" + username, HttpMethod.DELETE, entity, String.class)
				.getBody();
	}

	// Cart get cart
	@RequestMapping(value = "/getcart/{cartId}", method = RequestMethod.GET)
	public String getCart(@PathVariable("cartId") String cartId, Model model, Authentication authentication) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cart> entity = new HttpEntity<Cart>(headers);
		ResponseEntity<Cart> cart = restTemplate.exchange("http://cart-managment/cart/getcart/" + cartId,
				HttpMethod.GET, entity, Cart.class);
		model.addAttribute("cart", cart.getBody());
		model.addAttribute("details", authentication.getName());
		return "cart";

	}

	// Cart add item
	@Operation(summary = "Add Items to Cart")
	@RequestMapping(value = "/additem/{cartId}/{productId}", method = RequestMethod.POST)
	public String addCart(@Parameter(description = "Enter Cart Id") @PathVariable("cartId") String cartId,
			@Parameter(description = "Enter product Id") @PathVariable("productId") String productId) {
		CartProduct product = restTemplate.getForObject("http://product-managment/product/admin/getById/" + productId,
				CartProduct.class);
		Item item = new Item();
		if (product != null) {
			item.setProduct(product);
			item.setQuantity(1);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Item> entity = new HttpEntity<Item>(item, headers);
			restTemplate.exchange("http://cart-managment/cart/additem/" + cartId, HttpMethod.POST, entity, Cart.class);
			return "redirect:/web/getAllProducts";
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
		return restTemplate.exchange("http://cart-managment/cart/deleteitem/" + cartId + "/" + productId,
				HttpMethod.DELETE, entity, Cart.class);

	}

	// Cart delete all
	@RequestMapping(value = "/deleteCart/{cartId}", method = RequestMethod.DELETE)
	public ResponseEntity<Cart> deleteCart(@PathVariable("cartId") String cartId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cart> entity = new HttpEntity<Cart>(headers);
		return restTemplate.exchange("http://cart-managment/cart/deletecart/" + cartId, HttpMethod.DELETE, entity,
				Cart.class);

	}

	// Cart update
	@RequestMapping(value = "/updateitem/{cartId}/{productId}", method = RequestMethod.POST)
	@ResponseBody
	public String updateCart(@PathVariable("cartId") String cartId, Item item,
			@PathVariable("productId") String productId, Authentication authentication) {
		CartProduct product = restTemplate.getForObject("http://product-managment/product/admin/getById/" + productId,
				CartProduct.class);
		if (product != null) {
			item.setProduct(product);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Item> entity = new HttpEntity<Item>(item, headers);
			restTemplate.exchange("http://cart-managment/cart/updateitem/" + cartId, HttpMethod.PUT, entity,
					Cart.class);
			return "redirect:/web/getcart/" + authentication.getName();
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
		return restTemplate.exchange("http://order-managment/order/addOrder/" + customerId, HttpMethod.POST, entity,
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
				.getForEntity("http://order-managment/order/getOrder/" + customerId, Order[].class);
		return response.getBody();

	}

	// order get by current date
	@RequestMapping(value = "/getTodaysOrders/{customerId}", method = RequestMethod.GET)
	public Order[] getOrderByDate(@PathVariable("customerId") String customerId) {

		ResponseEntity<Order[]> response = restTemplate
				.getForEntity("http://order-managment/order/getTodaysOrders/" + customerId, Order[].class);
		return response.getBody();

	}

	// order get by customerId
	@RequestMapping(value = "/getByOrderId/{orderId}", method = RequestMethod.GET)
	public Order[] getByOrderId(@PathVariable("orderId") String orderId) {

		ResponseEntity<Order[]> response = restTemplate
				.getForEntity("http://order-managment/order/getByOrderId/" + orderId, Order[].class);
		return response.getBody();

	}

	// order delete by customerId
	@RequestMapping(value = "/deleteOrder/{orderId}", method = RequestMethod.DELETE)
	public String deleteOrder(@PathVariable("orderId") String orderId) {

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
