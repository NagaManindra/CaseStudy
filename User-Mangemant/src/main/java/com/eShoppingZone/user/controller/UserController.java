package com.eShoppingZone.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eShoppingZone.user.exception.UserNotFound;
import com.eShoppingZone.user.model.Users;
import com.eShoppingZone.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// User get by username
	@Operation(summary = "Get User by userName")
	@GetMapping("/{username}")
	public ResponseEntity<Users> getUserByUserName(
			@Parameter(description = "Enter User Name") @PathVariable("username") String username) throws Exception {

		Users userData = userService.getByUserName(username);
		if (userData == null) {
			throw new UserNotFound(username + " not found ");
		}
		return new ResponseEntity<>(userData, HttpStatus.OK);
	}

	// User update
	@Operation(summary = "Update User Details")
	@PutMapping("/update/{username}")
	public ResponseEntity<Users> updateUser(
			@Parameter(description = "Enter User Name") @PathVariable("username") String username,
			@Parameter(description = "Enter Update Details") @RequestBody Users user) throws Exception {

		Users userData = userService.getByUserName(username);
		if (userData != null) {
			user.setUserName(username);
			user.setRole(userData.getRole());
			user.setPassword(userData.getPassword());

			Users user2 = userService.updateUser(user);
			return new ResponseEntity<>(user2, HttpStatus.OK);
		} else {
			throw new UserNotFound(username + " not found ");
		}
	}

	// User register
	@Operation(summary = "Add new User")
	@PostMapping("/new/register")
	public ResponseEntity<Users> createUser(@Parameter(description = "Enter User Details") @RequestBody Users user) {
		Users userData = userService.getByUserName(user.getUserName());
		user.setRole("user");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (userData == null) {
			Users user2 = userService.createUser(user);
			return new ResponseEntity<>(user2, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
		}
	}

	// User delete
	@Operation(summary = "Delete User")

	@DeleteMapping("/delete/{username}")
	public ResponseEntity<String> deleteUser(
			@Parameter(description = "Enter userName") @PathVariable("username") String username) throws Exception {
		Users userData = userService.getByUserName(username);
		if (userData != null) {
			userService.deleteByUserName(username);
			return new ResponseEntity<>("user deleted", HttpStatus.NO_CONTENT);
		} else {
			throw new UserNotFound(username + " not found ");
		}
	}

	/*
	 * { "userName": "mani08", "fullName": "G Mani", "email": "mani08@gmail.com",
	 * "gender": "female", "dob": "19-03-1977", "role": "user", "mobile_no":
	 * 9515962633, "password": "mani@08", "address": { "house_no":89,
	 * "street_name":"Majest line", "colony_name":"BN", "city":"KNL", "state":"AP",
	 * "pincode":518001 } }
	 */

}
