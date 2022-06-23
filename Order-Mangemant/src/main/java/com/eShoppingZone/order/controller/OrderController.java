package com.eShoppingZone.order.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eShoppingZone.order.fallback.CartFallBack;
import com.eShoppingZone.order.fallback.UserFallBack;
import com.eShoppingZone.order.model.Cart;
import com.eShoppingZone.order.model.Order;
import com.eShoppingZone.order.model.User;
import com.eShoppingZone.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserFallBack userFallBack;
	@Autowired
	private CartFallBack cartFallBack;

	// Order create
	@Operation(summary = "To place Order")
	@PostMapping("/addOrder/{customerId}")
	public Order createOrder(
			@Parameter(description = "Enter Customer Id") @PathVariable("customerId") String customerId) {
		User user = userFallBack.getUserInfo(customerId);
		System.out.println(user.getFullName());
		Cart cart = cartFallBack.getCart(customerId);
		return orderService.createOrder(new Order(customerId, LocalDate.now(), cart.getTotalPrice(), "Order Failed",
				user.getAddress(), user.getMobile_no(), cart.getItems()));
	}

	// Order get all
	@Operation(summary = "To get All customer Orders")
	@GetMapping("/getOrder/{customerId}")
	public List<Order> getAllOrders(
			@Parameter(description = "Enter Customer Id") @PathVariable("customerId") String customerId) {
		return orderService.getByCustomerId(customerId);
	}

	// order get by current date
	@Operation(summary = "To get Oder by Current Date")
	@GetMapping("/getTodaysOrders/{customerId}")
	public List<Order> getTodaysOrders(
			@Parameter(description = "Enter Customer Id") @PathVariable("customerId") String customerId) {
		List<Order> list = orderService.getByCustomerId(customerId);
		List<Order> newList = new ArrayList<>();
		for (Order dateOrder : list) {
			if (dateOrder.getOrderDate().isEqual(LocalDate.now())) {
				newList.add(dateOrder);
			}
		}
		return newList;
	}

	// order get by orderId
	@Operation(summary = "To get customer Orders")
	@GetMapping("/getByOrderId/{orderId}")
	public Order getOrder(@Parameter(description = "Enter Order Id") @PathVariable("orderId") String orderId) {
		return orderService.getByOrderId(orderId);
	}

	// order Update by orderId
	@Operation(summary = "To Update customer Orders")
	@PutMapping("/updateOrder/{orderId}")
	public ResponseEntity<?> updateOrder(
			@Parameter(description = "Enter Order Id") @PathVariable("orderId") String orderId) {
		Order order = orderService.getByOrderId(orderId);
		order.setOrderStatus("Order Placed");
		Order order2 = orderService.createOrder(order);
		return new ResponseEntity<>(order2, HttpStatus.CREATED);
	}

	// order delete by customerId
	@Operation(summary = "Delete Orders by CustomerId")
	@DeleteMapping("/deleteOrder/{orderId}")
	public ResponseEntity<?> deleteOrder(
			@Parameter(description = "Enter Order Id") @PathVariable("orderId") String orderId) {
		orderService.deleteOrder(orderId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
