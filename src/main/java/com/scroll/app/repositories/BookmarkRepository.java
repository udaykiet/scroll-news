package com.scroll.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scroll.app.models.Bookmark;
import com.scroll.app.models.User;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark , Long> {

	List<Bookmark> findByUser(User user);
	boolean existsByUserIdAndNewsId(Long userId, Long newsId);
	void deleteByUserIdAndNewsId(Long userId, Long newsId);

}
