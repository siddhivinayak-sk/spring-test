package com.rps.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rps.model.WSMessage;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> exception(Throwable th) {
		return new ResponseEntity<String>(th.getMessage(), HttpStatus.OK);
	}
}
