package com.eShoppingZone.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("product/admin")
public class AdminProductController {
	@Autowired
	private ProductService productService;

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

	@PutMapping("/update/{productId}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product,
			@PathVariable("productId") String productId) throws Exception {
		Product productData = productService.getById(productId);
		if (productData != null) {
			Product product2 = product;
			return new ResponseEntity<>(productService.createProduct(product2), HttpStatus.OK);
		} else {
			throw new ProductNotFound(productId + " not found ");
		}
	}

	@DeleteMapping("/deleteproduct/{productId}")
	public ResponseEntity<Product> deleteById(@PathVariable String productId) throws Exception {

		Product productData = productService.getById(productId);
		if (productData != null) {
			productService.deleteByProductId(productId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			throw new ProductNotFound(productId + " not found ");
		}
	}

	@GetMapping("/getAll")
	public List<Product> getAllProducts() {

		return productService.getAll();
	}

	@GetMapping("/getById/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") String productId) throws Exception {
		Product productData = productService.getById(productId);

		if (productData == null) {
			throw new ProductNotFound(productId + " not found");
		}
		return new ResponseEntity<>(productData, HttpStatus.OK);
	}

	@GetMapping("/getByName/{productName}")
	public List<Product> getProductByName(@PathVariable("productName") String productName) throws Exception {

		List<Product> products = productService.getByName(productName);
		if (products.isEmpty())
			throw new ProductNotFound(productName + " not found");

		else {
			return products;
		}
	}

	@GetMapping("/getByCategory/{category}")
	public List<Product> getProductByCategory(@PathVariable("category") String category) throws Exception {

		List<Product> products = productService.getByCategory(category);
		if (products.isEmpty())
			throw new ProductNotFound(category + " not found");

		else {
			return products;
		}
	}

	@GetMapping("getByType/{productType}")
	public List<Product> getProductByType(@PathVariable("productType") String productType) throws Exception {

		List<Product> products = productService.getByType(productType);
		if (products.isEmpty())
			throw new ProductNotFound(productType + " not found");

		else {
			return products;
		}
	}

	/*
	 * { "productId":"123", "productType":"Full Sleeves",
	 * "productName":"TS Athiletic", "category":"T-Shirst", "image":"abc.jpg",
	 * "price":499.0, "rating":3.5, "description":"Fully Conforatable" }
	 */
}