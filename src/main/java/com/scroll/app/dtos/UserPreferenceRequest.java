package com.scroll.app.dtos;

import java.time.LocalTime;
import java.util.Set;

import com.scroll.app.models.News;
import com.scroll.app.models.UserPreference;
import lombok.Data;

@Data
public class UserPreferenceRequest {
	private Set<News.Category> preferredCategories;
	private boolean notificationEnabled;
	private LocalTime notificationTime;
	private String language;
	private boolean autoPlayVideos;
	private boolean saveDataMode;
	private boolean darkMode;
	private boolean showImages;
	private UserPreference.TextSize textSize;
	private boolean dailyDigestEnabled;
	private boolean weeklyDigestEnabled;
}
