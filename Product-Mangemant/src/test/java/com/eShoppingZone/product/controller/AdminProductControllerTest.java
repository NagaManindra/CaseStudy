package com.eShoppingZone.product.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.eShoppingZone.product.model.Product;
import com.eShoppingZone.product.service.ProductService;
import com.eShoppingZone.product.service.SequenceGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = AdminProductController.class)
class AdminProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ProductService service;

	@MockBean
	SequenceGeneratorService generatorService;

	Product product;
	String name;

	@BeforeAll
	static void beforeAll() {
		System.out.println("Running the test Cases");
	}

	@BeforeEach
	void BeforeEach() {
		product = new Product(123, "Full Sleeves", "TS Athiletic", "T-Shirst", "abc.jpg", 499.0, 3.5,
				"Fully Conforatable");
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
	@DisplayName("Get Product")
	void testGetProductByd() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		Mockito.when(service.getById(product.getProductId())).thenReturn(product);

		mockMvc.perform(MockMvcRequestBuilders.get("/admin/getById/" + product.getProductId())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", is(notNullValue()))).andExpect(jsonPath("$.productId", is(123)));
	}

	@Test
	@DisplayName("Create Product")
	void testCreateProduct() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		when(service.createProduct(product)).thenReturn(product);
		String bookJson = new ObjectMapper().writeValueAsString(product);
		mockMvc.perform(post("/admin/addProduct").contentType(MediaType.APPLICATION_JSON).content(bookJson))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Update Product")
	void testUpdateProduct() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		when(service.getById(product.getProductId())).thenReturn(product);
		when(service.createProduct(product)).thenReturn(product);
		String bookJson = new ObjectMapper().writeValueAsString(product);
		mockMvc.perform(put("/admin/update/" + product.getProductId()).contentType(MediaType.APPLICATION_JSON)
				.content(bookJson)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Delete Product")
	void testDeleteProduct() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		when(service.getById(product.getProductId())).thenReturn(product);
		when(service.deleteByProductId(product.getProductId())).thenReturn("product deleted");
		String bookJson = new ObjectMapper().writeValueAsString(product);
		mockMvc.perform(delete("/admin/deleteproduct/" + product.getProductId()).contentType(MediaType.APPLICATION_JSON)
				.content(bookJson)).andExpect(status().isNoContent());
	}

}
