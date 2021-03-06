package com.eShoppingZone.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eShoppingZone.cart.model.Cart;
import com.eShoppingZone.cart.repo.CartRepo;

@Service
public class CartService {

	@Autowired
	private CartRepo cartRepo;

	// create cart
	public Cart createCart(Cart cart) {
		return cartRepo.save(cart);
	}

	// get cart
	public Cart getCart(String cartId) {
		return cartRepo.findByCartId(cartId);
	}

	// update cart
	public Cart updateCart(Cart cart) {
		return cartRepo.save(cart);
	}

	// delete cart
	public Cart deleteCart(Cart cart) {
		return cartRepo.save(cart);
	}
}
