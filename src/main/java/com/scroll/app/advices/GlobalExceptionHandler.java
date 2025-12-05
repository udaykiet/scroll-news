package com.scroll.app.advices;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.scroll.app.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle ResourceNotFoundException Exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception) {
		ApiError error = ApiError.builder()
				.message(exception.getMessage())
				.errors(Collections.singletonList(exception.getMessage()))
				.status(HttpStatus.NOT_FOUND)
				.build();

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(error);

	}




	// Handle General Exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneralExceptions(Exception exception) {
		ApiError error = ApiError.builder()
				.message(exception.getMessage())
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.errors(Collections.singletonList("Something went wrong"))
				.build();

		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(error);
	}
}
