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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eShoppingZone.cart.model.Cart;
import com.eShoppingZone.cart.model.Item;
import com.eShoppingZone.cart.service.CartService;

@RestController
@RequestMapping("cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/additem/{cartId}")
	public ResponseEntity<?> addItemToCart(@RequestBody Item item, @PathVariable("cartId") String cartId) {
		try {
			Cart cart = cartService.getCart(cartId);

			if (cart != null) {
				List<Item> items = cart.getItems();
				for (Item value : items) {
					if (value.getProduct().getProductId().equals(item.getProduct().getProductId())) {
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
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		/*
		 * try { Cart cart = cartService.getCart(cartId); Product product =
		 * restTemplate.getForObject("http://localhost:9002/product/admin/getById/" +
		 * productId, Product.class); if (cart != null) { item.setProduct(product);
		 * List<Item> items = cart.getItems(); for (Item value : items) { if
		 * (value.getProduct().getProductId().equals(item.getProduct().getProductId()))
		 * { return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED); } }
		 * items.add(item); cart.setItems(items);
		 * cart.setTotalPrice(cart.getTotalPrice() + item.getProduct().getPrice() *
		 * item.getQuantity());
		 * 
		 * } else { List<Item> items = new ArrayList<>(); items.add(item);
		 * item.setProduct(product); cart = new Cart(); cart.setCartId(cartId);
		 * cart.setItems(items); cart.setTotalPrice(item.getProduct().getPrice() *
		 * item.getQuantity()); }
		 * 
		 * Cart updatedCart = cartService.createCart(cart);
		 * 
		 * return new ResponseEntity<>(updatedCart, HttpStatus.CREATED);
		 * 
		 * } catch (Exception e) { return new ResponseEntity<>(null,
		 * HttpStatus.INTERNAL_SERVER_ERROR); }
		 */
	}

	// Get Cart By User Id

	@GetMapping("/getcart/{cartId}")
	public ResponseEntity<?> getCartByUserId(@PathVariable("cartId") String cartId) {
		try {
			Cart cart = this.cartService.getCart(cartId);
			if (cart != null) {
				return new ResponseEntity<>(cart, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Cart is empty", HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Update Item in Cart

	@PutMapping("/updateitem/{cartId}")
	public ResponseEntity<?> updateItemInCart(@RequestBody Item item, @PathVariable("cartId") String cartId) {

		Cart cart = cartService.getCart(cartId);
		List<Item> items = cart.getItems();

		Item previousItem = new Item();
		double price;
		for (Item value : items) {
			if (value.getProduct().getProductId().equals(item.getProduct().getProductId())) {

				previousItem.setProduct(value.getProduct());
				previousItem.setQuantity(value.getQuantity());

				value.setQuantity(item.getQuantity());

			}
		}

		cart.setItems(items);
		if (previousItem.getQuantity() < item.getQuantity()) {
			price = cart.getTotalPrice() + ((item.getProduct().getPrice() * item.getQuantity())
					- previousItem.getProduct().getPrice() * previousItem.getQuantity());
			cart.setTotalPrice(price);
		} else {
			price = cart.getTotalPrice() - (previousItem.getProduct().getPrice() * previousItem.getQuantity()
					- item.getProduct().getPrice() * item.getQuantity());
			cart.setTotalPrice(price);
		}

		Cart updatedCart = cartService.updateCart(cart);

		return new ResponseEntity<>(updatedCart, HttpStatus.OK);

	}

	// Delete Item from Cart

	@DeleteMapping("/deleteitem/{cartId}/{productId}")
	public ResponseEntity<?> deleteItemFromCart(@PathVariable("productId") String productId,
			@PathVariable("cartId") String cartId) {
		try {
			Cart cart = this.cartService.getCart(cartId);
			List<Item> items = cart.getItems();
			Item item = items.stream().filter(x -> x.getProduct().getProductId().equals(productId)).findAny()
					.orElse(null);
			items.removeIf(x -> x.getProduct().getProductId().equals(productId));

			cart.setItems(items);
			cart.setTotalPrice(cart.getTotalPrice() - item.getProduct().getPrice() * item.getQuantity());

			Cart cartAfterDeleted = this.cartService.deleteCart(cart);

			return new ResponseEntity<>(cartAfterDeleted, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deletecart/{cartId}")
	public ResponseEntity<?> deleteCart(@PathVariable("cartId") String cartId) {
		try {
			Cart cart = this.cartService.getCart(cartId);
			cart.setTotalPrice(0);
			List<Item> items = new ArrayList<>();
			cart.setItems(items);
			cartService.updateCart(cart);
			return new ResponseEntity<>(cart, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
