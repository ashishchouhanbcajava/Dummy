package com.Dummy.Dummy.configurations;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.Dummy.Dummy.beans.Users;
import com.Dummy.Dummy.service.UsersService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UsersService usersService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws StreamWriteException, DatabindException, IOException {

		OAuth2User principal = (OAuth2User) authentication.getPrincipal();
		String email = principal.getAttribute("email");
		String profile = principal.getAttribute("profile");
		String token = JwtUtil.generateToken(email);

		Users user = (Users) usersService.loadUserByUsername(email);

		Users users = new Users(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), token,
				user.getContact());
		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		mapper.writeValue(writer, users);

	}
}
