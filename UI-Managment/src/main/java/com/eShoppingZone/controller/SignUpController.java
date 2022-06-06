package com.eShoppingZone.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.eShoppingZone.model.Users;

@Controller
public class SignUpController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/")
	public String indexPage() {

		return "login";
	}

	@RequestMapping(value = "/login")
	public String login(Model model) {

		model.addAttribute("error", "User Name not found");
		return "login";
	}

	@RequestMapping(value = "/adminHome")
	public String getAdminHome() {

		return "adminHome";
	}

	@RequestMapping(value = "/userHome")
	public String getUserHome() {

		return "/web/getAllProducts";
	}

	@RequestMapping(value = "/signUpPage")
	public String signUp() {
		return "signUp";
	}

	// User register
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String userRegister(@Validated @ModelAttribute(value = "user") Users user, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "signUp";
		}
		HttpHeaders headers = new HttpHeaders();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Users> entity = new HttpEntity<Users>(user, headers);
		ResponseEntity<Users> users = restTemplate.exchange("http://user-managment/user/register", HttpMethod.POST,
				entity, Users.class);
		System.out.println(users.hasBody());
		if (!users.hasBody()) {
			model.addAttribute("error", "Username already exits");
			model.addAttribute("user", user);
			return "signUp";
		}
		return "success";
	}

	// User update
	@RequestMapping(value = "/user/update/{username}", method = RequestMethod.POST)
	public String userUpdate(@PathVariable("username") String username, @ModelAttribute Users user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Users> entity = new HttpEntity<Users>(user, headers);
		restTemplate.exchange("http://user-managment/user/update/" + username, HttpMethod.PUT, entity, Users.class);
		return "redirect:/web/user/{username}";

	}

	@RequestMapping(value = "/success")
	public String success() {

		return "success";
	}
}
