package com.Dummy.Dummy.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Dummy.Dummy.beans.Users;
import com.Dummy.Dummy.enums.Role;
import com.Dummy.Dummy.service.UsersService;

import jakarta.validation.Valid;

@RequestMapping("/users")
@RestController
public class UsersRestController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private PasswordEncoder encoder;

	@PreAuthorize("hasAuthority(T(com.Dummy.Dummy.enums.Permissions).WRITE.name())")
	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody Users users) {

		if (usersService.existsByUsername(users.getUsername())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("user already exists with 'username' " + users.getUsername());
		}

		if (users.getUsername().toLowerCase().contains("admin")) {
			users.setRole(Role.ADMIN);
		} else {
			users.setRole(Role.USER);
		}
		users.setPassword(encoder.encode(users.getPassword()));
		return ResponseEntity.ok(usersService.save(users));
	}

	@PreAuthorize("hasAuthority(T(com.Dummy.Dummy.enums.Permissions).READ.name())")
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers() {

		if (usersService.getAll() != null) {
			return ResponseEntity.ok(usersService.getAll());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());

	}

}
