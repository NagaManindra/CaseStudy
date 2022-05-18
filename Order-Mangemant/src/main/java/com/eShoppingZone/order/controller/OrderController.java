package com.eShoppingZone.order.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserFallBack userFallBack;
	@Autowired
	private CartFallBack cartFallBack;

	@PostMapping("/addOrder/{customerId}")
	public Order createOrder(@PathVariable("customerId") String customerId) {
		User user = userFallBack.getUserInfo(customerId);
		Cart cart = cartFallBack.getCart(customerId);
		return orderService.createOrder(new Order(customerId, LocalDate.now(), cart.getTotalPrice(), "Order Placed",
				user.getAddress(), user.getMobile_no(), cart.getItems()));
	}

	@GetMapping("/getOrder/{customerId}")
	public List<Order> getAllOrders(@PathVariable("customerId") String customerId) {
		return orderService.getByCustomerId(customerId);
	}

	@GetMapping("/getTodaysOrders/{customerId}")
	public List<Order> getTodaysOrders(@PathVariable("customerId") String customerId) {
		List<Order> list = orderService.getByCustomerId(customerId);
		List<Order> newList = new ArrayList<>();
		for (Order dateOrder : list) {
			if (dateOrder.getOrderDate().isEqual(LocalDate.now())) {
				newList.add(dateOrder);
			}
		}
		return newList;
	}

	@GetMapping("/getByOrderId/{orderId}")
	public Order getOrder(@PathVariable("orderId") String orderId) {
		return orderService.getByOrderId(orderId);
	}

	@PutMapping("/updateOrder/{orderId}")
	public Order updateOrder(@PathVariable("orderId") String orderId) {
		Order order = orderService.getByOrderId(orderId);
		order.setOrderStatus("Order Accepted");
		return orderService.createOrder(order);
	}

	@DeleteMapping("/deleteOrder/{orderId}")
	public void deleteOrder(@PathVariable("orderId") String orderId) {
		orderService.deleteOrder(orderId);
	}
}
