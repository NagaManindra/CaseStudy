package com.eShoppingZone.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eShoppingZone.product.exeption.ProductNotFound;
import com.eShoppingZone.product.model.Product;
import com.eShoppingZone.product.service.ProductService;

@RestController
@RequestMapping("product/user")
public class UserProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/getAll")
	public List<Product> getAllProducts() {

		return productService.getAll();
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

}
