package com.eShoppingZone.user.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eShoppingZone.user.exception.UserNotFound;
import com.eShoppingZone.user.model.User;
import com.eShoppingZone.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/getAll")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String fullName) {
		try {
			List<User> users = new ArrayList<User>();

			if (fullName == null)
				userService.getAll().forEach(users::add);
			else
				userService.getByName(fullName);

			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> getUserById(@PathVariable("username") String username) throws Exception {

		User userData = userService.getByUserName(username);
		if (userData == null) {
			throw new UserNotFound(username + " not found ");
		}
		return new ResponseEntity<>(userData, HttpStatus.OK);
	}

	@PutMapping("/update/{username}")
	public ResponseEntity<User> updateUser(@PathVariable("username") String username, @RequestBody User user)
			throws Exception {

		User userData = userService.getByUserName(username);
		if (userData != null) {
			User user2 = userService.createUser(user);
			return new ResponseEntity<>(user2, HttpStatus.CREATED);
		} else {
			throw new UserNotFound(username + " not found ");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User userData = userService.getByUserName(user.getUserName());
		if (userData == null) {
			User user2 = userService.createUser(user);
			return new ResponseEntity<>(user2, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
		}
	}

	@DeleteMapping("/delete/{username}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("username") String username) throws Exception {
		User userData = userService.getByUserName(username);
		if (userData != null) {
			userService.deleteByUserName(username);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
