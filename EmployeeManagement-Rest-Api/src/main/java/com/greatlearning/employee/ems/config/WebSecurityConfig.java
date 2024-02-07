package com.greatlearning.employee.ems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.greatlearning.employee.ems.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public PasswordEncoder getBcrBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(getUserDetailsService());
		auth.setPasswordEncoder(getBcrBCryptPasswordEncoder());
		return auth;
	}

	public void configure(AuthenticationManagerBuilder builder) {
		builder.authenticationProvider(getAuthenticationProvider());
	}

	public void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz -> authz.requestMatchers("/api/employees/list").permitAll()
				.requestMatchers("/api/employees/new").hasAnyAuthority("ADMIN")
				.requestMatchers("/api/employees/view/**").hasAnyAuthority("ADMIN")
				.requestMatchers("/api/employees/update/**").hasAnyAuthority("ADMIN")
				.requestMatchers("/api/employees/delete/**").hasAuthority("ADMIN").anyRequest().fullyAuthenticated());
		http.cors(c -> c.disable());
		http.csrf(csrf -> csrf.disable());
		http.headers(customizer -> customizer.frameOptions(o -> o.disable()));
	}

}