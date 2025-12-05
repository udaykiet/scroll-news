package com.scroll.app.dtos;

import lombok.Data;

@Data
public class BookmarkRequestDto {
	private Long userId;
	private Long newsId;
}
