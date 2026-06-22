package com.Dummy.Dummy.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.Dummy.Dummy.beans.Users;
import com.Dummy.Dummy.configurations.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthenticationRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody Users request) {
		// TODO: process POST request

		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			if (authenticate.isAuthenticated()) {
				Users user = (Users) authenticate.getPrincipal();
				String token = JwtUtil.generateToken(request.getUsername());

				return ResponseEntity
						.ok(new Users(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), token));

			}
		} catch (Exception e) {
			throw e;
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
