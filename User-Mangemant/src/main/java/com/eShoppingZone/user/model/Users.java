package com.eShoppingZone.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "user")

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

	@Id
	private String userName;
	private String fullName;
	private String email;
	private String gender;
	private String dob;
	private String role = "user";
	private long mobile_no;
	private String password;
	private Address address;

}
