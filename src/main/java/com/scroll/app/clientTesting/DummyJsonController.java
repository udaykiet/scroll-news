package com.scroll.app.clientTesting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dummy")
@RequiredArgsConstructor
public class DummyJsonController {


	private final CallingAnotherApi callingAnotherApi;

	@GetMapping()
	public DummyPost getPost(){
		return callingAnotherApi.getPost();
	}

}
