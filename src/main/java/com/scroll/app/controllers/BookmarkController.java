package com.scroll.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scroll.app.services.BookmarkService;

@RestController
@RequestMapping("api/bookmark")
public class BookmarkController {

	private final BookmarkService bookmarkService;


	public BookmarkController(BookmarkService bookmarkService) {
		this.bookmarkService = bookmarkService;
	}


}
