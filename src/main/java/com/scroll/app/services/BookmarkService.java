package com.scroll.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scroll.app.exceptions.ResourceNotFoundException;
import com.scroll.app.models.Bookmark;
import com.scroll.app.models.News;
import com.scroll.app.models.User;
import com.scroll.app.repositories.BookmarkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final UserService userService;
	private final NewsService newsService;


	@Transactional
	public Bookmark addBookmark(User user, News news) {
		// Check if already bookmarked
		if (bookmarkRepository.existsByUserAndNews(user, news)) {
			throw new RuntimeException("Already bookmarked");
		}

		Bookmark bookmark = new Bookmark();
		bookmark.setUser(user);
		bookmark.setNews(news);

		return bookmarkRepository.save(bookmark);
	}


	@Transactional
	public void removeBookmark(User user, News news) {
		//check if it is bookmarked or not
		if(!bookmarkRepository.existsByUserAndNews(user , news)){
			throw new ResourceNotFoundException("Bookmark not found");
		}

		bookmarkRepository.deleteByUserAndNews(user, news);
	}

	public List<News> getUserBookmarks(User user) {
		return bookmarkRepository.findBookmarkedNewsByUser(user);
	}

	public boolean isBookmarked(User user, News news) {
		return bookmarkRepository.existsByUserAndNews(user, news);
	}

	public long getUserBookmarkCount(User user) {
		return bookmarkRepository.countByUser(user);
	}



}
