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

import com.Dummy.Dummy.beans.CustomAccessDeniedExceptionHandler;
import com.Dummy.Dummy.service.UsersService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService detailsService;

	@Autowired
	private CustomAccessDeniedExceptionHandler accessDeniedExceptionHandler;

	// basic auth configuration for sending username and passsword with each and
	// every request
	@Bean
	SecurityFilterChain config(HttpSecurity http) throws Exception {
		System.out.println("in security config");
		return http.csrf(csrf -> csrf.disable()).

				authorizeHttpRequests(au -> au.requestMatchers("/login", "/users/add").permitAll()
						.requestMatchers("/admin/**").hasAuthority("ADMIN").requestMatchers("/users/**")
						.hasAnyAuthority("ADMIN", "USER").anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.exceptionHandling(e -> e.accessDeniedHandler(accessDeniedExceptionHandler)).build();
	}

	// configuration for mvc form login with same daoAuthentication provider and
	// spring security automatically handles the logout you only have to provide
	// below form no endpoint is needed to process this logout url
	// <form th:action="@{/logout}" method="post">
	// <button type="submit">Logout</button>
	// </form>
//	@Bean
//	SecurityFilterChain formLoginConfig(HttpSecurity http) throws Exception {
//
//		return http
//				.authorizeHttpRequests(auth -> auth.requestMatchers("/src/main/resources/**", "/login").permitAll()
//						.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/users/**")
//						.hasAnyRole("USER", "ADMIN").anyRequest().authenticated())
//				.formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/paymentPage"))
//				.logout(l -> l.logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true)
//						.deleteCookies("JSESSIONID"))
//				.build();
//	}

	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	UserDetailsService getUserDetailsService() {
//		return new UsersService();
//	}

	@Bean
	AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(detailsService);
		daoAuthenticationProvider.setPasswordEncoder(getEncoder());
		return new ProviderManager(daoAuthenticationProvider);
	}
}
