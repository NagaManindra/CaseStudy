package com.eShoppingZone.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eShoppingZone.user.model.Users;
import com.eShoppingZone.user.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	// create User
	public Users createUser(Users user) {
		return userRepo.save(user);
	}

	// findAll users
	public List<Users> getAll() {
		return userRepo.findAll();
	}

	// find user by username
	public Users getByUserName(String username) {
		return userRepo.findByUserName(username);
	}

	// find user by name
	public List<Users> getByName(String name) {
		return userRepo.findByFullName(name);
	}

	// find user by email
	public Users getByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	// update user
	public Users updateUser(Users user) {
		Users user2 = userRepo.findByUserName(user.getUserName());
		user2.setFullName(user.getFullName());
		user2.setEmail(user.getEmail());
		user2.setGender(user.getGender());
		user2.setDob(user.getDob());
		user2.setRole(user.getRole());
		user2.setMobile_no(user.getMobile_no());
		user2.setPassword(user.getPassword());
		user2.setAddress(user.getAddress());

		return userRepo.save(user2);
	}

	// delete user by username
	public String deleteByUserName(String username) {
		Users user = userRepo.findByUserName(username);
		userRepo.delete(user);
		return "user deleted";
	}

	// delete user by email
	public String deleteByEmail(String email) {
		Users user = userRepo.findByEmail(email);
		userRepo.delete(user);
		return "user deleted";
	}

}