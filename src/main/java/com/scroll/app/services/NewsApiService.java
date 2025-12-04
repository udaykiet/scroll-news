package com.scroll.app.services;





import com.scroll.app.dtos.NewsApiResponse;
import com.scroll.app.models.News;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsApiService {

	@Value("${newsapi.key}")
	private String apiKey;

	@Value("${newsapi.url}")
	private String apiUrl;

	private final NewsService newsService;
	private final WebClient webClient = WebClient.create();




	public void fetchAndSaveNews(String country, String category) {
		try {
			String url = String.format("%s/top-headlines?country=%s&category=%s&apiKey=%s",
					apiUrl, country, category, apiKey);

			log.info("Fetching news from: {}", url);

			NewsApiResponse response = webClient.get()
					.uri(url)
					.retrieve()
					.bodyToMono(NewsApiResponse.class)
					.block();

			if (response != null && response.getArticles() != null) {
				log.info("Fetched {} articles", response.getArticles().size());

				int savedCount = 0;
				for (NewsApiResponse.Article article : response.getArticles()) {
					News news = convertToNews(article, category);
					if (news != null) {
						News saved = newsService.saveNews(news);
						if (saved != null) {
							savedCount++;
						}
					}
				}

				log.info("Successfully saved {} news articles", savedCount);
			}

		} catch (Exception e) {
			log.error("Error fetching news: {}", e.getMessage(), e);
		}
	}

	private News convertToNews(NewsApiResponse.Article article, String category) {
		try {
			News news = new News();
			news.setTitle(article.getTitle());
			news.setContent(article.getContent() != null ? article.getContent() : article.getDescription());
			news.setSummary(generateSummary(article.getDescription(), article.getContent()));
			news.setSource(article.getSource() != null ? article.getSource().getName() : "Unknown");
			news.setImageUrl(article.getUrlToImage());
			news.setUrl(article.getUrl());
			news.setCategory(mapCategory(category));

			// Parse published date
			if (article.getPublishedAt() != null) {
				try {
					news.setPublishedAt(LocalDateTime.parse(
							article.getPublishedAt(),
							DateTimeFormatter.ISO_DATE_TIME
					));
				} catch (Exception e) {
					news.setPublishedAt(LocalDateTime.now());
				}
			}

			return news;
		} catch (Exception e) {
			log.error("Error converting article: {}", e.getMessage());
			return null;
		}
	}

	private String generateSummary(String description, String content) {
		// Simple summary generation (first 60 words)
		String text = description != null ? description : content;
		if (text == null) return "No summary available";

		String[] words = text.split("\\s+");
		int wordCount = Math.min(60, words.length);

		StringBuilder summary = new StringBuilder();
		for (int i = 0; i < wordCount; i++) {
			summary.append(words[i]).append(" ");
		}

		return summary.toString().trim();
	}

	private News.Category mapCategory(String category) {
		if (category == null) return News.Category.GENERAL;

		return switch (category.toLowerCase()) {
			case "business" -> News.Category.BUSINESS;
			case "entertainment" -> News.Category.ENTERTAINMENT;
			case "health" -> News.Category.HEALTH;
			case "science" -> News.Category.SCIENCE;
			case "sports" -> News.Category.SPORTS;
			case "technology" -> News.Category.TECHNOLOGY;
			default -> News.Category.GENERAL;
		};
	}
}
