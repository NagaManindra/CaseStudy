package com.eShoppingZone.cart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eShoppingZone.cart.fallback.ProductFallBack;
import com.eShoppingZone.cart.model.Cart;
import com.eShoppingZone.cart.model.Item;
import com.eShoppingZone.cart.model.Product;
import com.eShoppingZone.cart.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/user")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductFallBack productFallBack;

	// Cart add item
	@Operation(summary = "Add Items to Cart")
	@PostMapping(value = "/additem/{cartId}/{productId}")
	public ResponseEntity<Cart> addCart(@Parameter(description = "Enter Cart Id") @PathVariable("cartId") String cartId,
			@Parameter(description = "Enter product Id") @PathVariable("productId") String productId) {
		Product product = productFallBack.getProduct(productId);
		Item item = new Item();
		item.setQuantity(1);
		item.setProduct(product);
		try {
			Cart cart = cartService.getCart(cartId);

			if (cart != null) {
				List<Item> items = cart.getItems();
				for (Item value : items) {
					if (value.getProduct().getProductId().equals(product.getProductId())) {
						return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
					}
				}

				items.add(item);
				cart.setItems(items);
				cart.setTotalPrice(cart.getTotalPrice() + item.getProduct().getPrice() * item.getQuantity());

			} else {
				List<Item> items = new ArrayList<>();
				items.add(item);

				cart = new Cart();
				cart.setCartId(cartId);
				cart.setItems(items);
				cart.setTotalPrice(item.getProduct().getPrice() * item.getQuantity());
			}

			Cart updatedCart = cartService.createCart(cart);

			return new ResponseEntity<>(updatedCart, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Get Cart By User Id
	@Operation(summary = "Get Cart by cartId")
	@GetMapping("/getcart/{cartId}")
	public ResponseEntity<Cart> getCartByUserId(
			@Parameter(description = "Enter Cart Id") @PathVariable("cartId") String cartId) {
		try {
			Cart cart = this.cartService.getCart(cartId);
			if (cart != null) {
				return new ResponseEntity<>(cart, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new Cart(cartId, 0, null), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Update Item in Cart
	@Operation(summary = "Update Items in Cart")
	@PutMapping("/updateitem/{cartId}/{productId}/{quantity}")
	public ResponseEntity<Cart> updateItemInCart(
			@Parameter(description = "Enter Cart Id") @PathVariable("cartId") String cartId,
			@Parameter(description = "Enter Quantity") @PathVariable("quantity") int quantity,
			@Parameter(description = "Enter Product Id") @PathVariable("productId") String productId) {

		Product product = productFallBack.getProduct(productId);
		Cart cart = cartService.getCart(cartId);
		List<Item> items = cart.getItems();

		Item previousItem = new Item();
		double price;
		for (Item value : items) {
			if (value.getProduct().getProductId().equals(product.getProductId())) {

				previousItem.setProduct(value.getProduct());
				previousItem.setQuantity(value.getQuantity());

				value.setQuantity(quantity);

			}
		}

		cart.setItems(items);
		if (previousItem.getQuantity() < quantity) {
			price = cart.getTotalPrice() + ((product.getPrice() * quantity)
					- previousItem.getProduct().getPrice() * previousItem.getQuantity());
			cart.setTotalPrice(price);
		} else {
			price = cart.getTotalPrice() - (previousItem.getProduct().getPrice() * previousItem.getQuantity()
					- product.getPrice() * quantity);
			cart.setTotalPrice(price);
		}

		Cart updatedCart = cartService.updateCart(cart);

		return new ResponseEntity<>(updatedCart, HttpStatus.OK);

	}

	// Delete Item from Cart
	@Operation(summary = "Delete Items in Cart")
	@DeleteMapping("/deleteitem/{cartId}/{productId}")
	public ResponseEntity<Cart> deleteItemFromCart(
			@Parameter(description = "Enter Cart Id") @PathVariable("cartId") String cartId,
			@Parameter(description = "Enter Product Id") @PathVariable("productId") String productId) {
		try {
			Cart cart = this.cartService.getCart(cartId);
			List<Item> items = cart.getItems();
			Item item = items.stream().filter(x -> x.getProduct().getProductId().equals(productId)).findAny()
					.orElse(new Item(null, 0));
			items.removeIf(x -> x.getProduct().getProductId().equals(productId));

			cart.setItems(items);
			cart.setTotalPrice(cart.getTotalPrice() - item.getProduct().getPrice() * item.getQuantity());

			Cart cartAfterDeleted = this.cartService.deleteCart(cart);

			return new ResponseEntity<>(cartAfterDeleted, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Cart delete all
	@Operation(summary = "Delete All Items in Cart")
	@DeleteMapping("/deletecart/{cartId}")
	public ResponseEntity<Cart> deleteCart(
			@Parameter(description = "Enter Cart Id") @PathVariable("cartId") String cartId) {
		try {
			Cart cart = this.cartService.getCart(cartId);
			cart.setTotalPrice(0);
			List<Item> items = new ArrayList<>();
			cart.setItems(items);
			cartService.updateCart(cart);
			return new ResponseEntity<>(cart, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
