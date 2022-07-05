package com.eShoppingZone.product.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eShoppingZone.product.model.Product;

@Repository
public interface ProductRepo extends MongoRepository<Product, Long> {
	Product findByProductId(long productId);

	List<Product> findByProductName(String productName);

	List<Product> findByProductType(String productType);

	List<Product> findByCategory(String category);

}
