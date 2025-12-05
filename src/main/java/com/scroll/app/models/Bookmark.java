package com.scroll.app.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "bookmarks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "news_id")
	private News news;


	private LocalDateTime bookmarkedAt;

	@PrePersist
	protected void onCreate() {
		bookmarkedAt = LocalDateTime.now();
	}
}