package com.eShoppingZone.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eShoppingZone.model.User;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

	List<User> findByFullName(String fullName);

	User findByUserName(String userName);

	User findByEmail(String username);

	List<User> findByRole(String role);

}
