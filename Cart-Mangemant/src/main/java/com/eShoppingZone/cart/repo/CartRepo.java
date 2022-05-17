package com.eShoppingZone.cart.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eShoppingZone.cart.model.Cart;

@Repository
public interface CartRepo extends MongoRepository<Cart, String> {
	Cart findByCartId(String cartId);
}
