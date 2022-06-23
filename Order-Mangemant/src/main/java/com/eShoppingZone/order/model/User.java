package com.eShoppingZone.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
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
