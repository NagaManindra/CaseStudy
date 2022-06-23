package com.eShoppingZone.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.eShoppingZone.user.model.Address;
import com.eShoppingZone.user.model.Users;
import com.eShoppingZone.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService service;

	Users users;
	String name;

	@BeforeAll
	static void beforeAll() {
		System.out.println("Running the test Cases");
	}

	@BeforeEach
	void BeforeEach() {

		Address address = new Address("89/2-2-11-3", "Majestic function hall line", "Balaji Nagar", "Kurnool",
				"Andhra Pradesh", 518006);
		users = new Users("ng2482", "Manindra", "ng2482@srmist.edu.in", "Male", "2001-06-25", "user", 9515962633l,
				"$2a$10$MyiC.OlXhMRCjARePBjDxOgZ4NYfle84OHQuGc1/TH7deL9i55SYi", address);
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
	public void testGetByUserName() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		Mockito.when(service.getByUserName("by2900")).thenReturn(users);

		mockMvc.perform(MockMvcRequestBuilders.get("/user/by2900").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Create User")
	public void testCreateUser() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		when(service.createUser(users)).thenReturn(users);
		String bookJson = new ObjectMapper().writeValueAsString(users);
		mockMvc.perform(post("/user/new/register").contentType(MediaType.APPLICATION_JSON).content(bookJson))
				.andExpect(status().isCreated());
	}

}
