package com.scroll.app.clientTesting;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CallingAnotherApi {

	private final WebClient webClient = WebClient.create();


	public DummyPost getPost(){
		try{
			DummyPost response =  webClient
					.get()
					.uri("https://dummyjson.com/posts/1")
					.retrieve()
					.bodyToMono(DummyPost.class)
					.block();

			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
