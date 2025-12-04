package com.scroll.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 500)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String summary;

	@Column(columnDefinition = "TEXT")
	private String content;

	private String source;

	@Enumerated(EnumType.STRING)
	private Category category;

	private String imageUrl;

	private String url;

	private LocalDateTime publishedAt;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}


	public enum Category {
		GENERAL, BUSINESS, ENTERTAINMENT,
		HEALTH, SCIENCE, SPORTS, TECHNOLOGY
	}
}
