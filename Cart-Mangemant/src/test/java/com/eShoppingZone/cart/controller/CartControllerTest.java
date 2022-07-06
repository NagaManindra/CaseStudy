package com.eShoppingZone.cart.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eShoppingZone.cart.fallback.ProductFallBack;
import com.eShoppingZone.cart.model.Cart;
import com.eShoppingZone.cart.model.Item;
import com.eShoppingZone.cart.model.Product;
import com.eShoppingZone.cart.service.CartService;

@WebMvcTest(value = CartController.class)
class CartControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CartService service;

	@MockBean
	ProductFallBack fallBack;

	Cart cart;
	Product product;
	Item items;
	String name;

	@BeforeAll
	static void beforeAll() {
		System.out.println("Running the test Cases");
	}

	@BeforeEach
	void BeforeEach() {
		product = new Product("123", "TS Athiletic", 499.0);
		items = new Item(product, 1);
		List<Item> item = new ArrayList<Item>();
		item.add(items);
		cart = new Cart("ng2482", 499.0, item);
		System.out.println("Test Case Started");
	}

	@AfterEach
	void afterEach() {
		System.out.println(name + " Runned Successfully");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("All test cases completed");
	}

	@Test
	@DisplayName("Get User")
	void testGetCartById() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();

		Mockito.when(service.getCart("ng2482")).thenReturn(cart);

		mockMvc.perform(MockMvcRequestBuilders.get("/user/getcart/ng2482")).andExpect(status().isOk())
				.andExpect(jsonPath("$", is(notNullValue()))).andExpect(jsonPath("$.cartId", is(cart.getCartId())));
	}

	@Test
	@DisplayName("Create cart")
	void testAddItemToCart() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		when(fallBack.getProduct(product.getProductId())).thenReturn(product);
		when(service.createCart(cart)).thenReturn(cart);
		mockMvc.perform(post("/user/additem/ng2482/123")).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Update cart")
	void testUpdateCart() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		when(fallBack.getProduct(product.getProductId())).thenReturn(product);
		when(service.getCart("ng2482")).thenReturn(cart);
		when(service.createCart(cart)).thenReturn(cart);
		mockMvc.perform(put("/user/updateitem/ng2482/123/2")).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Delete Item")
	void testDeleteItem() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		when(service.getCart("ng2482")).thenReturn(cart);
		when(service.createCart(cart)).thenReturn(cart);
		mockMvc.perform(delete("/user/deleteitem/ng2482/123")).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Delete Cart")
	void testDeleteCart() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		when(service.getCart("ng2482")).thenReturn(cart);
		when(service.createCart(cart)).thenReturn(cart);
		mockMvc.perform(delete("/user/deletecart/ng2482")).andExpect(status().isOk());
	}

}
