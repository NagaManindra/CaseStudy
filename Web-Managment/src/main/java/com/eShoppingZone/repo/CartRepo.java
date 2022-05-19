package com.eShoppingZone.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eShoppingZone.model.Cart;

@Repository
public interface CartRepo extends MongoRepository<Cart, String> {
	Cart findByCartId(String cartId);
}
