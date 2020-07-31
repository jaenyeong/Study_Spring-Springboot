package com.jaenyeong.springboot_started.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SecurityRunner implements ApplicationRunner {

	@Autowired
	SecurityAccountService accountService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		SecurityAccount jaenyeong = accountService.createAccount("jaenyeong", "1234");
		System.out.println("Security User Name : " + jaenyeong.getUserName()
				+ " Password : " + jaenyeong.getPassword());
	}
}
