package com.scroll.app.services;



import java.util.HashSet;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scroll.app.dtos.UserPreferenceRequest;
import com.scroll.app.dtos.UserPreferenceResponse;
import com.scroll.app.exceptions.ResourceNotFoundException;
import com.scroll.app.models.News;
import com.scroll.app.models.User;
import com.scroll.app.models.UserPreference;
import com.scroll.app.repositories.UserPreferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserPreferenceService {

	private final UserPreferenceRepository preferenceRepository;
	private final UserService userService;
	private final ModelMapper modelMapper;

	/**
	 * Create default preferences for new user
	 */
	@Transactional
	public UserPreference createDefaultPreferences(User user) {
		UserPreference preference = new UserPreference();
		preference.setUser(user);
		preference.setPreferredCategories(new HashSet<>());
		preference.setNotificationEnabled(true);
		preference.setLanguage("en");
		preference.setTextSize(UserPreference.TextSize.MEDIUM);

		log.info("Created default preferences for user: {}", user.getId());
		return preferenceRepository.save(preference);
	}


	/**
	 * Get user preferences
	 */
	public UserPreferenceResponse getUserPreferences(Long userId) {
		UserPreference preference =  preferenceRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		return modelMapper.map(preference , UserPreferenceResponse.class);
	}


	/**
	 * Update user preferences
	 */
	@Transactional
	public UserPreference updatePreferences(Long userId, UserPreferenceRequest request) {
		UserPreference preference = preferenceRepository.findByUserId(userId)
				.orElseGet(() -> {
					User user = userService.getUserById(userId)
							.orElseThrow(() -> new RuntimeException("User not found"));
					return createDefaultPreferences(user);
				});

		// Update all fields
		if (request.getPreferredCategories() != null) {
			preference.setPreferredCategories(request.getPreferredCategories());
		}

		preference.setNotificationEnabled(request.isNotificationEnabled());

		if (request.getNotificationTime() != null) {
			preference.setNotificationTime(request.getNotificationTime());
		}

		if (request.getLanguage() != null) {
			preference.setLanguage(request.getLanguage());
		}

		preference.setAutoPlayVideos(request.isAutoPlayVideos());
		preference.setSaveDataMode(request.isSaveDataMode());
		preference.setDarkMode(request.isDarkMode());
		preference.setShowImages(request.isShowImages());

		if (request.getTextSize() != null) {
			preference.setTextSize(request.getTextSize());
		}

		preference.setDailyDigestEnabled(request.isDailyDigestEnabled());
		preference.setWeeklyDigestEnabled(request.isWeeklyDigestEnabled());

		log.info("Updated preferences for user: {}", userId);
		return preferenceRepository.save(preference);
	}


	/**
	 * Add a preferred category
	 */
	@Transactional
	public UserPreference addPreferredCategory(Long userId, News.Category category) {
		UserPreference preference = preferenceRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Preferences not found"));

		preference.addPreferredCategory(category);

		log.info("Added category {} for user: {}", category, userId);
		return preferenceRepository.save(preference);
	}

	/**
	 * Remove a preferred category
	 */
	@Transactional
	public UserPreference removePreferredCategory(Long userId, News.Category category) {
		UserPreference preference = preferenceRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Preferences not found"));

		preference.removePreferredCategory(category);

		log.info("Removed category {} for user: {}", category, userId);
		return preferenceRepository.save(preference);
	}


	/**
	 * Check if user prefers a category
	 */
	public boolean userPrefersCategory(Long userId, News.Category category) {
		return preferenceRepository.findByUserId(userId)
				.map(pref -> pref.hasPreferredCategory(category))
				.orElse(false);
	}


	/**
	 * Toggle notification
	 */
	@Transactional
	public UserPreference toggleNotifications(Long userId) {
		UserPreference preference = preferenceRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Preferences not found"));

		preference.setNotificationEnabled(!preference.isNotificationEnabled());

		log.info("Toggled notifications for user: {}", userId);
		return preferenceRepository.save(preference);
	}

	/**
	 * Toggle dark mode
	 */
	@Transactional
	public UserPreference toggleDarkMode(Long userId) {
		UserPreference preference = preferenceRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Preferences not found"));

		preference.setDarkMode(!preference.isDarkMode());

		log.info("Toggled dark mode for user: {}", userId);
		return preferenceRepository.save(preference);
	}

}
