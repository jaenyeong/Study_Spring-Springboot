package com.jaenyeong.springboot_started.htmlunit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlunitController {

	@GetMapping("/htmlunit/hello")
	public String hello(Model model) {
		model.addAttribute("name", "jaenyeong");
		return "hello";
	}
}
