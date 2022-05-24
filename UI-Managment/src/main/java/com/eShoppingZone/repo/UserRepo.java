package com.eShoppingZone.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eShoppingZone.model.Users;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {

	List<Users> findByFullName(String fullName);

	Users findByUserName(String userName);

	Users findByEmail(String username);

	List<Users> findByRole(String role);

}
