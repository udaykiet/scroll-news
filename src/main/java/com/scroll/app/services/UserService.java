package com.scroll.app.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.scroll.app.exceptions.ResourceNotFoundException;
import com.scroll.app.models.User;
import com.scroll.app.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {


	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	public User createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		// TODO: Hash password here (we'll add security later)
		return userRepository.save(user);
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Transactional
	public void updateLastLogin(Long userId) {
		userRepository.findById(userId).ifPresent(user -> {
			user.setLastLoginAt(LocalDateTime.now());
			userRepository.save(user);
		});
	}

	public boolean emailExists(String email) {
		return userRepository.existsByEmail(email);
	}
}
