package com.eShoppingZone.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Users {

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
