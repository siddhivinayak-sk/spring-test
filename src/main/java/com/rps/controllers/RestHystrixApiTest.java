package com.rps.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@RequestMapping("/hystrix")
public class RestHystrixApiTest {

	@RequestMapping("/t1")
	@HystrixCommand(
			fallbackMethod = "fallback_hello",
			commandProperties = {
//					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
			}
	)
	public ResponseEntity<String> t1() throws InterruptedException {
		Thread.sleep(3000);
		return new ResponseEntity("Hello", HttpStatus.OK);
	}
	
	
	private ResponseEntity<String> fallback_hello() {
		return new ResponseEntity("Hystrix Hello", HttpStatus.OK);
	}
}
