package com.eShoppingZone.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eShoppingZone.product.model.Product;
import com.eShoppingZone.product.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	// create products
	public Product createProduct(Product product) {
		return productRepo.save(new Product(product.getProductId(), product.getProductType(), product.getProductName(),
				product.getCategory(), product.getImage(), product.getPrice(), product.getRating(),
				product.getDescription()));
	}

	// get all products
	public List<Product> getAll() {
		return productRepo.findAll();
	}

	// get product by id
	public Product getById(String productID) {
		return productRepo.findByProductId(productID);
	}

	// get products by type
	public List<Product> getByType(String productType) {
		return productRepo.findByProductType(productType);
	}

	// get products by type
	public List<Product> getByName(String productName) {
		return productRepo.findByProductName(productName);
	}

	// get products by type
	public List<Product> getByCategory(String category) {
		return productRepo.findByCategory(category);
	}

	// update product
	public Product update(Product product) {
		Product product2 = productRepo.findByProductId(product.getProductId());
		product2.setProductType(product.getProductType());
		product2.setProductName(product.getProductName());
		product2.setCategory(product.getCategory());
		product2.setImage(product.getImage());
		product2.setPrice(product.getPrice());
		product2.setRating(product.getRating());
		product2.setDescription(product.getDescription());

		return productRepo.save(product2);
	}

	// delete product by productId
	public void deleteByProductId(String productId) {
		Product product = productRepo.findByProductId(productId);
		productRepo.delete(product);
	}

}
