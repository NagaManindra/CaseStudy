package com.eShoppingZone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableConfigurationProperties
@EnableWebSecurity
@CrossOrigin(origins = "http://localhost:3000")

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	MongoUserDetailsService mongoUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// disabling Cross-Site Request Forgery
		http.csrf().disable();
		// no authentication required
		// http.authorizeRequests().antMatchers("/", "/login", "/logout",
		// "/signUp").permitAll();
		// authenticated users
		http.authorizeRequests().antMatchers("/login").permitAll();
		// http.authorizeRequests()
		// .antMatchers("/api/product/user/**", "/api/user/user/**",
		// "/api/cart/user/**", "/api/order/user/**")
		// .permitAll();

		// restricting access
		http.authorizeRequests()
				.antMatchers("/api/user/admin/**", "/api/product/admin/**", "/api/cart/admin/**", "/api/order/admin/**")
				.hasAnyAuthority("admin").and().exceptionHandling();

		// http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
		// configure login form
		http.authorizeRequests().and().formLogin().and().httpBasic();//
		// .usernameParameter("username").passwordParameter("password")

		// Config for Logout Page
		// .and().logout().logoutUrl("/logout");
		http.cors();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(mongoUserDetailsService);
	}

}
