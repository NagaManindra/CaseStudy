package com.eShoppingZone.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.eShoppingZone.model.Cart;
import com.eShoppingZone.model.CartProduct;
import com.eShoppingZone.model.Item;
import com.eShoppingZone.model.Order;
import com.eShoppingZone.model.Product;
import com.eShoppingZone.model.Users;
import com.eShoppingZone.model.Wallet;
import com.paytm.pg.merchant.PaytmChecksum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Controller
@RequestMapping("/web")
public class WebController {

	@Autowired
	private Wallet wallet;

	@Autowired
	private Environment env;

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
				.getForEntity("http://product-managment/user/getByName/" + productName, Product[].class);
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

		ResponseEntity<Product[]> response = restTemplate.getForEntity("http://product-managment/user/getAll",
				Product[].class);
		model.addAttribute("list", response.getBody());
		model.addAttribute("details", authentication.getName());
		return "home";
	}

	// Product get by category
	@RequestMapping(value = "/getByCategory/{category}", method = RequestMethod.GET)
	public Product[] getBycategory(@PathVariable("category") String category) {

		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://product-managment/user/getByCategory/" + category, Product[].class);
		return response.getBody();

	}

	// Product get by type
	@RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
	public Product[] getByType(@PathVariable("type") String type) {

		ResponseEntity<Product[]> response = restTemplate
				.getForEntity("http://product-managment/user/getByType/" + type, Product[].class);
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
		ResponseEntity<Cart> cart = restTemplate.exchange("http://cart-managment/user/getcart/" + cartId,
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
		CartProduct product = restTemplate.getForObject("http://product-managment/user/getById/" + productId,
				CartProduct.class);
		Item item = new Item();
		if (product != null) {
			item.setProduct(product);
			item.setQuantity(1);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Item> entity = new HttpEntity<Item>(item, headers);
			restTemplate.exchange("http://cart-managment/user/additem/" + cartId + "/" + productId, HttpMethod.POST,
					entity, Cart.class);
			return "redirect:/web/getAllProducts";
		}
		return null;

	}

	// Cart delete item
	@RequestMapping(value = "/deleteItem/{cartId}/{productId}", method = RequestMethod.GET)
	public String deleteCartItem(@PathVariable("cartId") String cartId, @PathVariable("productId") String productId,
			Authentication authentication) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cart> entity = new HttpEntity<Cart>(headers);
		restTemplate.exchange("http://cart-managment/user/deleteitem/" + cartId + "/" + productId, HttpMethod.DELETE,
				entity, Cart.class);
		return "redirect:/web/getcart/" + authentication.getName();

	}

	// Cart delete all
	@RequestMapping(value = "/deleteCart/{cartId}", method = RequestMethod.GET)
	public String deleteCart(@PathVariable("cartId") String cartId, Authentication authentication) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cart> entity = new HttpEntity<Cart>(headers);
		restTemplate.exchange("http://cart-managment/user/deletecart/" + cartId, HttpMethod.DELETE, entity, Cart.class);
		return "redirect:/web/getcart/" + authentication.getName();

	}

	// Cart update
	@RequestMapping(value = "/updateitem/{cartId}/{productId}", method = RequestMethod.POST)

	public String updateCart(@PathVariable("cartId") String cartId, @RequestParam("quantity") int quantity,
			@PathVariable("productId") String productId, Authentication authentication) {
		CartProduct product = restTemplate.getForObject("http://product-managment/user/getById/" + productId,
				CartProduct.class);
		if (product != null) {
			Item item = new Item(quantity);
			item.setProduct(product);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Item> entity = new HttpEntity<Item>(item, headers);
			restTemplate.exchange("http://cart-managment/user/updateitem/" + cartId + "/" + productId, HttpMethod.PUT,
					entity, Cart.class);
			return "redirect:/web/getcart/" + authentication.getName();
		}
		return null;
	}

	// Order create
	@RequestMapping(value = "/addOrder/{customerId}", method = RequestMethod.GET)
	public String addOrder(@PathVariable("customerId") String customerId, Authentication authentication) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Order> entity = new HttpEntity<Order>(headers);

		restTemplate.exchange("http://order-managment/user/addOrder/" + customerId, HttpMethod.POST, entity,
				Order.class);
		restTemplate.exchange("http://cart-managment/user/deletecart/" + customerId, HttpMethod.DELETE, entity,
				Cart.class);
		return "redirect:/web/getTodaysOrders/" + authentication.getName();

	}

	// Order get all
	@RequestMapping(value = "/getOrder/{customerId}", method = RequestMethod.GET)
	public String getOrder(@PathVariable("customerId") String customerId, Model model, Authentication authentication) {
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON);
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 * HttpEntity<Order> entity = new HttpEntity<Order>(headers); return
		 * restTemplate.exchange("http://localhost:9004/order/getOrder/" + customerId,
		 * HttpMethod.GET, entity, Order.class);
		 */
		ResponseEntity<Order[]> response = restTemplate
				.getForEntity("http://order-managment/user/getOrder/" + customerId, Order[].class);
		model.addAttribute("order", response.getBody());
		model.addAttribute("details", authentication.getName());
		return "order";

	}

	// order get by current date
	@RequestMapping(value = "/getTodaysOrders/{customerId}", method = RequestMethod.GET)
	public String getOrderByDate(@PathVariable("customerId") String customerId, Model model,
			Authentication authentication) {

		ResponseEntity<Order[]> response = restTemplate
				.getForEntity("http://order-managment/user/getTodaysOrders/" + customerId, Order[].class);
		model.addAttribute("order", response.getBody());
		model.addAttribute("details", authentication.getName());
		return "order";

	}

	// order get by customerId
	@RequestMapping(value = "/getByOrderId/{orderId}", method = RequestMethod.GET)
	public Order[] getByOrderId(@PathVariable("orderId") String orderId) {

		ResponseEntity<Order[]> response = restTemplate
				.getForEntity("http://order-managment/user/getByOrderId/" + orderId, Order[].class);
		return response.getBody();

	}

	// order delete by customerId
	@RequestMapping(value = "/deleteOrder/{orderId}", method = RequestMethod.DELETE)
	public String deleteOrder(@PathVariable("orderId") String orderId) {

		restTemplate.exchange("http://order-managment/user/deleteOrder/" + orderId, HttpMethod.DELETE, null,
				String.class);

		return orderId + " deleted";
	}

	@RequestMapping(value = "/wallet/{customerId}/{totalprice}", method = RequestMethod.GET)
	public String wallet(@PathVariable("customerId") String customerId, @PathVariable("totalprice") double totalprice,
			Model model) {

		// Order order =
		// restTemplate.getForObject("http://order-managment/order/addOrder/" +
		// customerId, Order.class);
		model.addAttribute("customerId", customerId);
		model.addAttribute("price", totalprice);
		return "delete";
	}

	@PostMapping(value = "/submitPaymentDetail")
	public ModelAndView getRedirect(@RequestParam(name = "CUST_ID") String customerId,
			@RequestParam(name = "TXN_AMOUNT") String transactionAmount) throws Exception {
		ModelAndView modelAndView = new ModelAndView("redirect:" + wallet.getPaytmUrl());
		TreeMap<String, String> parameters = new TreeMap<>();
		wallet.getDetails().forEach((k, v) -> parameters.put(k, v));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Order> entity = new HttpEntity<Order>(headers);
		ResponseEntity<Order> order = restTemplate.exchange("http://order-managment/user/addOrder/" + customerId,
				HttpMethod.POST, entity, Order.class);
		restTemplate.exchange("http://cart-managment/user/deletecart/" + customerId, HttpMethod.DELETE, entity,
				Cart.class);

		parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
		parameters.put("EMAIL", env.getProperty("paytm.email"));
		parameters.put("ORDER_ID", order.getBody().getOrderId());
		parameters.put("TXN_AMOUNT", transactionAmount);
		parameters.put("CUST_ID", customerId);
		String checkSum = getCheckSum(parameters);
		parameters.put("CHECKSUMHASH", checkSum);
		modelAndView.addAllObjects(parameters);
		return modelAndView;
	}

	@PostMapping(value = "/pgresponse")
	public String getResponseRedirect(HttpServletRequest request, Model model, Authentication authentication) {

		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		String paytmChecksum = "";
		for (Entry<String, String[]> requestParamsEntry : mapData.entrySet()) {
			if ("CHECKSUMHASH".equalsIgnoreCase(requestParamsEntry.getKey())) {
				paytmChecksum = requestParamsEntry.getValue()[0];
			} else {
				parameters.put(requestParamsEntry.getKey(), requestParamsEntry.getValue()[0]);
			}
		}
		String result;

		boolean isValideChecksum = false;
		System.out.println("RESULT : " + parameters.toString());
		try {
			isValideChecksum = validateCheckSum(parameters, paytmChecksum);
			if (isValideChecksum && parameters.containsKey("RESPCODE")) {
				if (parameters.get("RESPCODE").equals("01")) {
					result = "Payment Successful";
				} else {
					result = "Payment Failed";
				}
			} else {
				result = "Checksum mismatched";
			}
		} catch (Exception e) {
			result = e.toString();
		}
		model.addAttribute("result", result);
		parameters.remove("CHECKSUMHASH");
		model.addAttribute("parameters", parameters);
		model.addAttribute("details", authentication.getName());
		return "report";
	}

	private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
		return PaytmChecksum.verifySignature(parameters, wallet.getMerchantKey(), paytmChecksum);
	}

	private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
		return PaytmChecksum.generateSignature(parameters, wallet.getMerchantKey());
	}
}
