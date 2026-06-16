package com.Dummy.Dummy.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Dummy.Dummy.beans.Users;
import com.Dummy.Dummy.service.UsersService;

@RequestMapping("/users")
@RestController
public class UsersRestController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private PasswordEncoder encoder;

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Users users) {

		if (users.getUsername().contains("admin")) {
			users.setRole("ADMIN");
		} else {
			users.setRole("USER");
		}
		users.setPassword(encoder.encode(users.getPassword()));
		return ResponseEntity.ok(usersService.save(users));
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers() {

		if (usersService.getAll() != null) {
			return ResponseEntity.ok(usersService.getAll());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());

	}

}
