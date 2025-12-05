package com.scroll.app.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "bookmarks",
		uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_id", "news_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "news_id", nullable = false)
	private News news;

	@Column(updatable = false)
	private LocalDateTime bookmarkedAt;

	@PrePersist
	protected void onCreate() {
		bookmarkedAt = LocalDateTime.now();
	}
}