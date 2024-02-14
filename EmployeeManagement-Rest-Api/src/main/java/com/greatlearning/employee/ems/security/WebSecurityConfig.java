package com.greatlearning.employee.ems.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.greatlearning.employee.ems.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder BcrBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService UserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(UserDetailsService());
		auth.setPasswordEncoder(BcrBCryptPasswordEncoder());
		return auth;
	}

	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.authenticationProvider(getAuthenticationProvider());
	}

	@SuppressWarnings({ "deprecation", "removal" })
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().requestMatchers("/api/employees/list", "/api/employees/view/{id}")
		.hasAnyAuthority("ADMIN", "USER")
		.requestMatchers("/api/employees/addRole", "/api/employees/addUser", "/api/employees/update/{id}",
				"/api/employees/delete/{id}", "/api/employees/search", "/api/employees/sort")
		.hasAuthority("admin").anyRequest().authenticated();
		http.csrf().disable();
		http.cors().disable();
	}
}
