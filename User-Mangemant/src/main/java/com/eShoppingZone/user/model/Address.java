package com.eShoppingZone.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	private String house_no;
	private String street_name;
	private String colony_name;
	private String city;
	private String state;
	private int pincode;
}
