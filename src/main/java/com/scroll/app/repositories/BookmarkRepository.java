package com.scroll.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scroll.app.models.Bookmark;
import com.scroll.app.models.News;
import com.scroll.app.models.User;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark , Long> {

	List<Bookmark> findByUserOrderByBookmarkedAtDesc(User user);

	Optional<Bookmark> findByUserAndNews(User user, News news);

	boolean existsByUserAndNews(User user, News news);

	void deleteByUserAndNews(User user, News news);

	@Query("SELECT b.news FROM Bookmark b WHERE b.user = ?1 ORDER BY b.bookmarkedAt DESC")
	List<News> findBookmarkedNewsByUser(User user);

	long countByUser(User user);
}

