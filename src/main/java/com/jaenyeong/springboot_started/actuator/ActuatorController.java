package com.jaenyeong.springboot_started.actuator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActuatorController {

	@GetMapping("/act/hello")
	public String hello() {
		return "hello";
	}
}
