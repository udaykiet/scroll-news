package com.scroll.app.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// ONE-TO-ONE relationship with User
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	// Favorite Categories (can select multiple)
	@ElementCollection(targetClass = News.Category.class)
	@CollectionTable(name = "user_preferred_categories",
			joinColumns = @JoinColumn(name = "preference_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private Set<News.Category> preferredCategories = new HashSet<>();


	// Notification Settings
	@Column(name = "notification_enabled")
	private boolean notificationEnabled = true;

	@Column(name = "notification_time")
	private LocalTime notificationTime = LocalTime.of(9, 0); // Default 9 AM

	// Language Preference
	@Column(name = "language")
	private String language = "en"; // English by default

	// Reading Preferences
	@Column(name = "auto_play_videos")
	private boolean autoPlayVideos = false;

	@Column(name = "save_data_mode")
	private boolean saveDataMode = false; // Load low-res images

	@Column(name = "dark_mode")
	private boolean darkMode = false;


	// Content Preferences
	@Column(name = "show_images")
	private boolean showImages = true;

	@Column(name = "text_size")
	@Enumerated(EnumType.STRING)
	private TextSize textSize = TextSize.MEDIUM;


	// Email Digest
	@Column(name = "daily_digest_enabled")
	private boolean dailyDigestEnabled = false;

	@Column(name = "weekly_digest_enabled")
	private boolean weeklyDigestEnabled = false;


	// Timestamps
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	// Enums
	public enum TextSize {
		SMALL, MEDIUM, LARGE, EXTRA_LARGE
	}


	// Helper Methods
	public void addPreferredCategory(News.Category category) {
		this.preferredCategories.add(category);
	}

	public void removePreferredCategory(News.Category category) {
		this.preferredCategories.remove(category);
	}

	public boolean hasPreferredCategory(News.Category category) {
		return this.preferredCategories.contains(category);
	}
}
