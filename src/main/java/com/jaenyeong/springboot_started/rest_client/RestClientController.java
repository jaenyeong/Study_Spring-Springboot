package com.jaenyeong.springboot_started.rest_client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestClientController {

	@GetMapping("/rest/hello")
	public String hello() throws InterruptedException {
		Thread.sleep(5000L);
		return "hello";
	}

	@GetMapping("/rest/world")
	public String world() throws InterruptedException {
		Thread.sleep(3000L);
		return "world";
	}
}
