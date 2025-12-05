package com.scroll.app.dtos;

import java.time.LocalDateTime;

import com.scroll.app.models.News;
import lombok.Data;

@Data
public class BookmarkResponseDto {
	private Long id;
	private News news;
	private LocalDateTime bookmarkedAt;
}
