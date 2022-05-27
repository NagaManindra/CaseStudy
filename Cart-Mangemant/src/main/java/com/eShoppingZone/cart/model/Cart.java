package com.eShoppingZone.cart.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "cart")

@Data
@NoArgsConstructor

public class Cart {
	@Id
	private String cartId;
	private double totalPrice;
	private List<Item> items;

}
