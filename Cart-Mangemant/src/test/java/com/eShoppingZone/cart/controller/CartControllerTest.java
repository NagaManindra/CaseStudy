package com.eShoppingZone.cart.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

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

	@Autowired
	private RestTemplate restTemplate;

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
		cart = new Cart("ng2482", 0, item);
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
	public void testGetCartById() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();

		Mockito.when(service.getCart("ng2482")).thenReturn(cart);

		mockMvc.perform(MockMvcRequestBuilders.get("/user/getcart/ng2482").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	/*
	 * @Test
	 * 
	 * @DisplayName("Create User") public void testCreateUser() throws Exception {
	 * name = new Object() { }.getClass().getEnclosingMethod().getName();
	 * when(restTemplate.getForEntity("http://localhost:9002/user/getById/123",
	 * Product.class)) .thenReturn(new ResponseEntity<>(product, HttpStatus.OK));
	 * when(service.createCart(cart)).thenReturn(cart); String bookJson = new
	 * ObjectMapper().writeValueAsString(cart);
	 * mockMvc.perform(post("/user/additem/ng2482/123").contentType(MediaType.
	 * APPLICATION_JSON).content(bookJson)) .andExpect(status().isOk()); }
	 */

}
