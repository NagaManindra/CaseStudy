package com.eShoppingZone.product.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eShoppingZone.product.exeption.ProductNotFound;
import com.eShoppingZone.product.model.Product;
import com.eShoppingZone.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
public class AdminProductController {
	@Autowired
	private ProductService productService;

	// Product register
	@Operation(summary = "To Add Product")
	@PostMapping("/addProduct")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {

		Product productData = productService.getById(product.getProductId());
		if (productData == null) {
			Product product2 = productService.createProduct(product);
			return new ResponseEntity<Product>(product2, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
		}
	}

	// Product update
	@Operation(summary = "To Update the Product")
	@PutMapping("/update/{productId}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product,
			@Parameter(description = "Enter Product Id") @PathVariable("productId") String productId) throws Exception {
		Product productData = productService.getById(productId);
		if (productData != null) {
			Product product2 = product;
			return new ResponseEntity<>(productService.createProduct(product2), HttpStatus.OK);
		} else {
			throw new ProductNotFound(productId + " not found ");
		}
	}

	// Product Delete
	@Operation(summary = "To Delete the Product")
	@DeleteMapping("/deleteproduct/{productId}")
	public void deleteById(@Parameter(description = "Enter Product Id") @PathVariable String productId)
			throws Exception {

		Product productData = productService.getById(productId);
		if (productData != null) {
			productService.deleteByProductId(productId);
		} else {
			throw new ProductNotFound(productId + " not found ");
		}
	}

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
			throws Exception {

		List<Product> products = productService.getByName(productName);
		if (products.isEmpty())
			throw new ProductNotFound(productName + " not found");

		else {
			return products;
		}
	}

	// Product get by category
	@Operation(summary = "Get Product by Category")
	@GetMapping("/getByCategory/{category}")
	public List<Product> getProductByCategory(
			@Parameter(description = "Enter Product Category") @PathVariable("category") String category)
			throws Exception {

		List<Product> products = productService.getByCategory(category);
		if (products.isEmpty())
			throw new ProductNotFound(category + " not found");

		else {
			return products;
		}
	}

	// Product get by type
	@Operation(summary = "Get Product by Type")
	@GetMapping("getByType/{productType}")
	public List<Product> getProductByType(
			@Parameter(description = "Enter Product Type") @PathVariable("productType") String productType)
			throws Exception {

		List<Product> products = productService.getByType(productType);
		if (products.isEmpty())
			throw new ProductNotFound(productType + " not found");

		else {
			return products;
		}
	}

	// Product get by type
	@Operation(summary = "Get Product by Id")
	@GetMapping("/getById/{productId}")
	public ResponseEntity<Product> getProductById(
			@Parameter(description = "Enter Product Id") @PathVariable("productId") String productId) throws Exception {
		Product productData = productService.getById(productId);

		if (productData == null) {
			throw new ProductNotFound(productId + " not found");
		}
		return new ResponseEntity<>(productData, HttpStatus.OK);
	}

	/*
	 * { "productId":"123", "productType":"Full Sleeves",
	 * "productName":"TS Athiletic", "category":"T-Shirst", "image":"abc.jpg",
	 * "price":499.0, "rating":3.5, "description":"Fully Conforatable" }
	 */
}
