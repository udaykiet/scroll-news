package com.scroll.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scroll.app.models.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark , Long> {
}
