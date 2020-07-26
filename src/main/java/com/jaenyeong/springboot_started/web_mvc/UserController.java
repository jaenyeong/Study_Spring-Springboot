package com.jaenyeong.springboot_started.web_mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/user")
	public String user() {
		return "user";
	}

	//	@PostMapping("/user")
////	@ResponseBody
//	public User create(@RequestBody User user) {
//		return null;
//	}

	@PostMapping("/users/create")
//	@ResponseBody
	public User create(@RequestBody User user) {
		System.out.println(user);
		return user;
	}
}
