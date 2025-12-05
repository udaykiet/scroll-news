package com.scroll.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scroll.app.models.User;
import com.scroll.app.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

	private final UserService userService;


	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
		try {
			User savedUser = userService.createUser(user);

			Map<String, Object> response = new HashMap<>();
			response.put("message", "User registered successfully");
			response.put("userId", savedUser.getId());
			response.put("email", savedUser.getEmail());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return userService.getUserById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		return userService.getUserByEmail(email)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

}
