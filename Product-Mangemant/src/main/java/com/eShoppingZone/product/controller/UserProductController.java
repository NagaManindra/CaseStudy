package com.eShoppingZone.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eShoppingZone.product.exeption.ProductNotFound;
import com.eShoppingZone.product.model.Product;
import com.eShoppingZone.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/user")
public class UserProductController {

	@Autowired
	private ProductService productService;

	String notFound = " not Found";

	// Product get all
	@Operation(summary = "To Display All the Product")
	@GetMapping("/getAll")
	public List<Product> getAllProducts() {

		return productService.getAll();
	}

	// Product get by name
	@Operation(summary = "Find Product by its Name")
	@GetMapping("/getByName/{productName}")
	public List<Product> getProductByName(
			@Parameter(description = "Enter Product Name") @PathVariable("productName") String productName)
			throws ProductNotFound {

		List<Product> products = productService.getByName(productName);
		if (products.isEmpty())
			throw new ProductNotFound(productName + notFound);

		else {
			return products;
		}
	}

	// Product get by category
	@Operation(summary = "Get Product by Category")
	@GetMapping("/getByCategory/{category}")
	public List<Product> getProductByCategory(
			@Parameter(description = "Enter Product Category") @PathVariable("category") String category)
			throws ProductNotFound {

		List<Product> products = productService.getByCategory(category);
		if (products.isEmpty())
			throw new ProductNotFound(category + notFound);

		else {
			return products;
		}
	}

	// Product get by type
	@Operation(summary = "Get Product by Type")
	@GetMapping("getByType/{productType}")
	public List<Product> getProductByType(
			@Parameter(description = "Enter Product Type") @PathVariable("productType") String productType)
			throws ProductNotFound {

		List<Product> products = productService.getByType(productType);
		if (products.isEmpty())
			throw new ProductNotFound(productType + notFound);

		else {
			return products;
		}
	}

	// Product get by Id
	@Operation(summary = "Get Product by Id")
	@GetMapping("/getById/{productId}")
	public ResponseEntity<Product> getProductById(
			@Parameter(description = "Enter Product Id") @PathVariable("productId") long productId)
			throws ProductNotFound {
		Product productData = productService.getById(productId);

		if (productData == null) {
			throw new ProductNotFound(productId + notFound);
		}
		return new ResponseEntity<>(productData, HttpStatus.OK);
	}

}
