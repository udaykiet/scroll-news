package com.scroll.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scroll.app.models.News;
import com.scroll.app.services.NewsApiService;
import com.scroll.app.services.NewsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NewsController {


	private final NewsService newsService;
	private final NewsApiService newsApiService;

	/**
	 * Get latest news with pagination
	 */
	@GetMapping
	public ResponseEntity<Page<News>> getLatestNews(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<News> news = newsService.getLatestNews(page, size);
		return ResponseEntity.ok(news);
	}

	/**
	 * Get all news (without pagination)
	 */
	@GetMapping("/all")
	public ResponseEntity<List<News>> getAllNews() {
		List<News> news = newsService.getAllNews();
		return ResponseEntity.ok(news);
	}

	/**
	 * Get news by category
	 */
	@GetMapping("/search/{category}")
	public ResponseEntity<Page<News>> getNewsByCategory(
			@PathVariable News.Category category,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<News> news = newsService.getNewsByCategory(category, page, size);
		return ResponseEntity.ok(news);
	}


	/**
	 * Get single news by ID
	 */
	@GetMapping("/{id}")
	public ResponseEntity<News> getNewsById(@PathVariable Long id) {
		News news = newsService.getNewsById(id);
		if (news == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(news);
	}

	/**
	 * Manually fetch news from NewsAPI
	 */
	@PostMapping("/fetch")
	public ResponseEntity<Map<String, String>> fetchNews(
			@RequestParam(defaultValue = "in") String country,
			@RequestParam(defaultValue = "general") String category) {

		newsApiService.fetchAndSaveNews(country, category);

		Map<String, String> response = new HashMap<>();
		response.put("message", "News fetched successfully");
		response.put("country", country);
		response.put("category", category);

		return ResponseEntity.ok(response);
	}


	/**
	 * Health check endpoint
	 */
	@GetMapping("/health")
	public ResponseEntity<Map<String, Object>> health() {
		Map<String, Object> response = new HashMap<>();
		response.put("status", "UP");
		response.put("totalNews", newsService.getNewsCount());
		return ResponseEntity.ok(response);
	}


	/**
	 * Create news manually (for testing)
	 */
	@PostMapping
	public ResponseEntity<News> createNews(@RequestBody News news) {
		News savedNews = newsService.saveNews(news);
		return ResponseEntity.ok(savedNews);
	}
}
