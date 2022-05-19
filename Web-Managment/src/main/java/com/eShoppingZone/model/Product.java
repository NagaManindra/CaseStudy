package com.eShoppingZone.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "products")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	private String productId;
	private String productType;
	private String productName;
	private String category;
	private String image;
	private Double price;
	private Double rating;
	private String description;
}
