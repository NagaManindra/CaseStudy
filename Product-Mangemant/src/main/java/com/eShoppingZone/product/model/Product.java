package com.eShoppingZone.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "products")

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	@Id
	private long productId;
	private String productType;
	private String productName;
	private String category;
	private String image;
	private Double price;
	private Double rating;
	private String description;
}
