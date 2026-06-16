package com.Dummy.Dummy.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.Dummy.Dummy.service.UsersService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// basic auth configuration for sending username and passsword with each and
	// every request

	@Bean
	SecurityFilterChain config(HttpSecurity http) throws Exception {
		System.out.println("in security config");
		return http.csrf(csrf -> csrf.disable()).

				authorizeHttpRequests(
						au -> au.requestMatchers("/login", "/users/add").permitAll().anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults()).build();
	}

	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService getUserDetailsService() {
		return new UsersService();
	}

	@Bean
	AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(getEncoder());
		return new ProviderManager(daoAuthenticationProvider);
	}
}
