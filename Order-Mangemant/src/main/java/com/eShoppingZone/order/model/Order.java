package com.eShoppingZone.order.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order")
public class Order {

	@Id
	private String orderId;
	private String customerId;
	private LocalDate orderDate;
	private double totalPrice;
	private String orderStatus;
	private Address address;
	private Long mobile_No;
	private List<Item> items;

	public Order() {
		super();
	}

	public Order(String customerId, LocalDate orderDate, double totalPrice, String orderStatus, Address address,
			Long mobile_No, List<Item> items) {
		super();
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.orderStatus = orderStatus;
		this.address = address;
		this.mobile_No = mobile_No;
		this.items = items;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getMobile_No() {
		return mobile_No;
	}

	public void setMobile_No(Long mobile_No) {
		this.mobile_No = mobile_No;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
