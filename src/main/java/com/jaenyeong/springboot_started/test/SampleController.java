package com.jaenyeong.springboot_started.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private SampleService sampleService;

	@GetMapping("/hello")
	public String hello() {
//		logger.info("Sample Ctr logger.info");
		System.out.println("Sample Ctr System.out.println");
		System.out.println("ABC");
		return "Hello Spring " + sampleService.getName();
	}

	@GetMapping("/devtools")
	public String devtools() {
		return "Devtools";
	}
}
