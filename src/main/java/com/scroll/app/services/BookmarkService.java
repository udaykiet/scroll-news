package com.scroll.app.services;

import org.springframework.stereotype.Service;

import com.scroll.app.repositories.BookmarkRepository;

@Service
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;

	public BookmarkService(BookmarkRepository bookmarkRepository) {
		this.bookmarkRepository = bookmarkRepository;
	}



}
