package com.eShoppingZone.user.controller;

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
		users = Users.builder().userName("ng2482").fullName("Manindra").email("ng2482@srmist.edu.in").gender("Male")
				.dob("2001-06-25").role("user").mobile_no(9515962633l).password("Naga@7550").address(address).build();
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
	void testGetByUserName() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		Mockito.when(service.getByUserName(users.getUserName())).thenReturn(users);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/user/" + users.getUserName()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.userName", is(users.getUserName())));
	}

	@Test
	@DisplayName("Create User")
	void testCreateUser() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		Mockito.when(service.getByUserName(users.getUserName())).thenReturn(null);

		when(service.createUser(users)).thenReturn(users);
		String bookJson = new ObjectMapper().writeValueAsString(users);
		mockMvc.perform(post("/user/new/register").contentType(MediaType.APPLICATION_JSON).content(bookJson))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("InValid User Details")
	void testInvalidUser() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		Address address = new Address("89/2-2-11-3", "Majestic function hall line", "Balaji Nagar", "Kurnool",
				"Andhra Pradesh", 518006);
		Users users1 = Users.builder().userName("ng24").fullName("Manindra").email("ng2482@srmist.edu.in")
				.gender("Male").dob("2001-06-25").role("user").mobile_no(9515962633l).password("Naga@7550")
				.address(address).build();
		System.out.println("Test Case Started");
		Mockito.when(service.getByUserName(users.getUserName())).thenReturn(users);

		when(service.createUser(users)).thenReturn(users1);
		String bookJson = new ObjectMapper().writeValueAsString(users1);
		mockMvc.perform(post("/user/new/register").contentType(MediaType.APPLICATION_JSON).content(bookJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Update User")
	void testUpdateUser() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		when(service.getByUserName(users.getUserName())).thenReturn(users);
		when(service.updateUser(users)).thenReturn(users);
		String bookJson = new ObjectMapper().writeValueAsString(users);
		mockMvc.perform(
				put("/user/update/" + users.getUserName()).contentType(MediaType.APPLICATION_JSON).content(bookJson))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Delete User")
	void testDeleteUser() throws Exception {
		name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		when(service.getByUserName("by2900")).thenReturn(users);
		when(service.deleteByUserName(users.getUserName())).thenReturn("user deleted");
		mockMvc.perform(delete("/user/delete/by2900").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

}
