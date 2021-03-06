package com.eShoppingZone.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "user")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private String userName;
	private String fullName;
	private String email;
	private String gender;
	private String dob;
	private String role;
	private long mobile_no;
	private String password;
	private Address address;

}
