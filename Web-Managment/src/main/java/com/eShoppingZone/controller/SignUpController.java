package com.eShoppingZone.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.eShoppingZone.model.Users;

@Controller
public class SignUpController {

	@Autowired
	private PasswordEncoder passwordEncoder;

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
	public String userRegister(@Valid @ModelAttribute("user") Users user, BindingResult result) {

		if (result.hasErrors()) {
			return "signUp";
		}
		HttpHeaders headers = new HttpHeaders();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Users> entity = new HttpEntity<Users>(user, headers);
		restTemplate.exchange("http://user-managment/user/register", HttpMethod.POST, entity, Users.class);

		return "login";

	}

	@RequestMapping(value = "/success")
	public String success() {

		return "success";
	}
}
