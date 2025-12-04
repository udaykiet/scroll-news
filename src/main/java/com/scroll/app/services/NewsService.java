package com.scroll.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scroll.app.enums.CategoryEnum;
import com.scroll.app.models.News;
import com.scroll.app.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {


	private final NewsRepository newsRepository;

	public Page<News> getLatestNews(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size,
				Sort.by("publishedAt").descending());
		return newsRepository.findAll(pageRequest);
	}

	public Page<News> getNewsByCategory(News.Category category, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size,
				Sort.by("publishedAt").descending());
		return newsRepository.findByCategory(category, pageRequest);
	}

	public News saveNews(News news) {
		// Check for duplicates
		if (news.getUrl() != null && news.getTitle() != null) {
			if (newsRepository.existsByUrlAndTitle(news.getUrl(), news.getTitle())) {
				log.debug("Duplicate news found: {}", news.getTitle());
				return null;
			}
		}
		return newsRepository.save(news);
	}

	public News getNewsById(Long id) {
		return newsRepository.findById(id).orElse(null);
	}

	public List<News> getAllNews() {
		return newsRepository.findAll(Sort.by("publishedAt").descending());
	}

	public long getNewsCount() {
		return newsRepository.count();
	}
}
