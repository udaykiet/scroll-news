package com.scroll.app.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scroll.app.dtos.UserPreferenceRequest;
import com.scroll.app.dtos.UserPreferenceResponse;
import com.scroll.app.models.News;
import com.scroll.app.models.UserPreference;
import com.scroll.app.services.UserPreferenceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserPreferenceController {


	private final UserPreferenceService preferenceService;

	/**
	 * Get user preferences
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<UserPreferenceResponse> getUserPreferences(@PathVariable Long userId) {
		return ResponseEntity.ok(preferenceService.getUserPreferences(userId));

	}

	/**
	 * Update user preferences
	 */
	@PutMapping("/{userId}")
	public ResponseEntity<UserPreference> updatePreferences(
			@PathVariable Long userId,
			@RequestBody UserPreferenceRequest request) {

		UserPreference updated = preferenceService.updatePreferences(userId, request);
		return ResponseEntity.ok(updated);
	}

	/**
	 * Add preferred category
	 */
	@PostMapping("/{userId}/categories/{category}")
	public ResponseEntity<Map<String, String>> addCategory(
			@PathVariable Long userId,
			@PathVariable News.Category category) {

		preferenceService.addPreferredCategory(userId, category);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Category added successfully");
		response.put("category", category.toString());

		return ResponseEntity.ok(response);
	}


	/**
	 * Remove preferred category
	 */
	@DeleteMapping("/{userId}/categories/{category}")
	public ResponseEntity<Map<String, String>> removeCategory(
			@PathVariable Long userId,
			@PathVariable News.Category category) {

		preferenceService.removePreferredCategory(userId, category);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Category removed successfully");
		response.put("category", category.toString());

		return ResponseEntity.ok(response);
	}


	/**
	 * Toggle notifications
	 */
	@PostMapping("/{userId}/notifications/toggle")
	public ResponseEntity<Map<String, Object>> toggleNotifications(@PathVariable Long userId) {
		UserPreference preference = preferenceService.toggleNotifications(userId);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Notification settings updated");
		response.put("notificationEnabled", preference.isNotificationEnabled());

		return ResponseEntity.ok(response);
	}

	/**
	 * Toggle dark mode
	 */
	@PostMapping("/{userId}/darkmode/toggle")
	public ResponseEntity<Map<String, Object>> toggleDarkMode(@PathVariable Long userId) {
		UserPreference preference = preferenceService.toggleDarkMode(userId);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Dark mode updated");
		response.put("darkMode", preference.isDarkMode());

		return ResponseEntity.ok(response);
	}
}
