package com.scroll.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scroll.app.dtos.BookmarkRequestDto;
import com.scroll.app.models.Bookmark;
import com.scroll.app.models.News;
import com.scroll.app.models.User;
import com.scroll.app.services.BookmarkService;
import com.scroll.app.services.NewsService;
import com.scroll.app.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/bookmark")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookmarkController {

	private final BookmarkService bookmarkService;

	private final UserService userService;
	private final NewsService newsService;


	/**
	 * Add a bookmark
	 */
	@PostMapping
	public ResponseEntity<Map<String, Object>> addBookmark(@RequestBody BookmarkRequestDto request) {
		try {
			User user = userService.getUserById(request.getUserId())
					.orElseThrow(() -> new RuntimeException("User not found"));

			News news = newsService.getNewsById(request.getNewsId());
			if (news == null) {
				throw new RuntimeException("News not found");
			}

			Bookmark bookmark = bookmarkService.addBookmark(user, news);

			Map<String, Object> response = new HashMap<>();
			response.put("message", "Bookmarked successfully");
			response.put("bookmarkId", bookmark.getId());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	/**
	 * Remove a bookmark
	 */
	@DeleteMapping
	public ResponseEntity<Map<String, String>> removeBookmark(@RequestBody BookmarkRequestDto request) {
		try {
			User user = userService.getUserById(request.getUserId())
					.orElseThrow(() -> new RuntimeException("User not found"));

			News news = newsService.getNewsById(request.getNewsId());
			if (news == null) {
				throw new RuntimeException("News not found");
			}

			bookmarkService.removeBookmark(user, news);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Bookmark removed successfully");

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}


	/**
	 * Get all bookmarks for a user
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<News>> getUserBookmarks(@PathVariable Long userId) {
		User user = userService.getUserById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		List<News> bookmarks = bookmarkService.getUserBookmarks(user);
		return ResponseEntity.ok(bookmarks);
	}

	/**
	 * Check if news is bookmarked
	 */
	@GetMapping("/check")
	public ResponseEntity<Map<String, Boolean>> checkBookmark(
			@RequestParam Long userId,
			@RequestParam Long newsId) {

		User user = userService.getUserById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		News news = newsService.getNewsById(newsId);
		if (news == null) {
			throw new RuntimeException("News not found");
		}

		boolean isBookmarked = bookmarkService.isBookmarked(user, news);

		Map<String, Boolean> response = new HashMap<>();
		response.put("isBookmarked", isBookmarked);

		return ResponseEntity.ok(response);
	}

	/**
	 * Get bookmark count for user
	 */
	@GetMapping("/user/{userId}/count")
	public ResponseEntity<Map<String, Long>> getBookmarkCount(@PathVariable Long userId) {
		User user = userService.getUserById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		long count = bookmarkService.getUserBookmarkCount(user);

		Map<String, Long> response = new HashMap<>();
		response.put("count", count);

		return ResponseEntity.ok(response);
	}
}
