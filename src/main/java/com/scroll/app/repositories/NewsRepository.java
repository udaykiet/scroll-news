package com.scroll.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.scroll.app.models.News;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

	Page<News> findByCategory(News.Category category, Pageable pageable);

	List<News> findTop20ByOrderByPublishedAtDesc();

	boolean existsByUrlAndTitle(String url, String title);
}
