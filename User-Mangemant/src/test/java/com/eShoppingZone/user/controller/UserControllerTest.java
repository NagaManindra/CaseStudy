package com.eShoppingZone.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.eShoppingZone.user.model.Address;
import com.eShoppingZone.user.model.Users;
import com.eShoppingZone.user.service.UserService;

class UserControllerTest {

	Users users = new Users();
	Address address = new Address();
	UserService controller;

	@BeforeEach
	void beforeEach() {
		controller = new UserService();
	}

	@Test
	@DisplayName("Post User")
	void test() {
		address.setHouse_no("2-2-11-3");
		address.setColony_name("BA");
		address.setStreet_name("MJ");
		address.setCity("KNL");
		address.setState("AP");
		address.setPincode(123456);

		users.setAddress(address);
		users.setUserName("max3002");
		users.setFullName("Baymax");
		users.setEmail("naga@gmail.com");
		users.setDob("27-08-1999");
		users.setGender("male");
		users.setMobile_no(9515923333l);

		assertEquals(users, controller.createUser(users));

	}

}
