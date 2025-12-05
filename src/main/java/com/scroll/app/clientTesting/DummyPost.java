package com.scroll.app.clientTesting;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DummyPost {

	private int id;
	private String title;
	private String body;
	private List<String> tags;
	private String views;
	private String userId;
	private Reactions reactions;


	@Data
	public static class Reactions{
		private String likes;
		private String dislikes;
	}
}