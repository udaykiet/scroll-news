package com.scroll.app.advices;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {


	@Builder.Default
	private boolean success = false;

	private HttpStatus status;
	private String message;
	private List<String> errors;

	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();
}
