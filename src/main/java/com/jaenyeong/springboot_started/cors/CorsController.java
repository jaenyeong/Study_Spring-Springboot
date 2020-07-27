package com.jaenyeong.springboot_started.cors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {

	@CrossOrigin(origins = "http://locahost:8888")
	@GetMapping("/cors/hello")
	public String hello() {
		return "Cors Hello";
	}
}
