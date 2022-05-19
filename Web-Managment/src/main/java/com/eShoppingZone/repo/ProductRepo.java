package com.eShoppingZone.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eShoppingZone.model.CartProduct;
import com.eShoppingZone.model.Product;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
	CartProduct findByProductId(String productId);

	List<CartProduct> findByProductName(String productName);

	List<CartProduct> findByProductType(String productType);

	List<CartProduct> findByCategory(String category);

}
