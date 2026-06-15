package com.Dummy.Dummy.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// basic auth configuration for sending username and passsword with each and
	// every request
	@Bean
	SecurityFilterChain config(HttpSecurity http) throws Exception {

		return http.authorizeHttpRequests(au -> au.anyRequest().authenticated()).httpBasic(Customizer.withDefaults())
				.build();
	}
}
