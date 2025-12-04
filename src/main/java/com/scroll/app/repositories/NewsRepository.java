package com.scroll.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scroll.app.models.News;

@Repository
public interface NewsRepository extends JpaRepository<News , Long> {
}
