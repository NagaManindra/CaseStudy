package com.eShoppingZone.model;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

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
public class Users {

	@Id
	@Pattern(regexp = "[A-Za-z0-9]{6,20}", message = "Username must be Alphanumeric and should have lenght 8 to 20")
	private String userName;
	private String fullName;
	@Email(message = "Enter valid email")
	private String email;
	private String gender;
	private String dob;
	private String role;
	@Digits(message = "Number should contain 10 digits.", fraction = 0, integer = 10)
	private long mobile_no;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&.])[A-Za-z\\d$@$!%*?&.]{8,20}", message = "Password should contain at least one upper- case letter, lower-case letter, a digit or special character (_, $, #, ., @). Password should also be 8 to 20 characters long.")
	private String password;
	@Valid
	private Address address;

}
