package com.Dummy.Dummy.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import com.Dummy.Dummy.Filters.JwtAuthFilter;
import com.Dummy.Dummy.beans.CustomAccessDeniedExceptionHandler;
import com.Dummy.Dummy.enums.Permissions;
import com.Dummy.Dummy.enums.Role;
import com.Dummy.Dummy.service.UsersService;

import okhttp3.internal.http.HttpMethod;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService detailsService;

	@Autowired
	private JwtAuthFilter authFilter;

	@Autowired
	private CustomAccessDeniedExceptionHandler accessDeniedExceptionHandler;
//-------------Basic Auth--------------------------------------------------------------
	// basic auth configuration for sending username and passsword with each and
	// every request at here we have used hasAuthority and hasAnyAuthority to check
	// the user role beacause we have overriden getAuthorities() method in our user
	// class in which we have save String role as it is but when the authorization
	// manager matches the role of specific user it matches like below code so we
	// use hasAuthority and hasAnyAuthority ,if we saved the role or return the
	// authority like ROLE_USER, ROLE_ADMIN then we can use the hasRole() method

//	public static <T> AuthorityAuthorizationManager<T> hasRole(String role) {

//		Assert.notNull(role, "role cannot be null");

//		Assert.isTrue(!role.startsWith(ROLE_PREFIX), () -> role + " should not start with " + ROLE_PREFIX + " since "

//				+ ROLE_PREFIX + " is automatically prepended when using hasRole. Consider using hasAuthority instead.");

//		return hasAuthority(ROLE_PREFIX + role);

//	}
	@Bean
	SecurityFilterChain config(HttpSecurity http) throws Exception {
		System.out.println("in security config");
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(au -> au.requestMatchers("/login", "/authenticate").permitAll()
						.requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
//						.requestMatchers(org.springframework.http.HttpMethod.POST, "/users/**")
//						.hasAnyAuthority(Permissions.WRITE.name())
						.anyRequest().authenticated())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//				.httpBasic(Customizer.withDefaults())  //commented basic auth for implementing jwt filter 
				.exceptionHandling(e -> e.accessDeniedHandler(accessDeniedExceptionHandler)).build();
	}

//	-------------------Simple form login Auth----------------------------------

	// configuration for mvc form login with same daoAuthentication provider and
	// spring security automatically handles the logout you only have to provide
	// below form no endpoint is needed to process this logout url
	// <form th:action="@{/logout}" method="post">
	// <button type="submit">Logout</button>
	// </form>
//	@Bean
//	SecurityFilterChain formLoginConfig(HttpSecurity http) throws Exception {
//
//		return http.csrf(c -> c.ignoringRequestMatchers("/verify", "/createOrder"))
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
