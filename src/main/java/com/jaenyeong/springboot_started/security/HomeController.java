package com.jaenyeong.springboot_started.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/security/hello")
	public String hello() {
		return "security-hello";
	}

	@GetMapping("/security/my")
	public String my() {
		return "security-my";
	}
}
