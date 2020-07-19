package com.jaenyeong.springboot_started.runner;

import com.jaenyeong.springboot_started.properties.JaenyeongProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ProfileRunner implements ApplicationRunner {
	@Autowired
	private String hello;

	@Autowired
	private JaenyeongProperties jaenyeongProperties;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("----------------------------------------------------------");
		System.out.println("hello : " + hello);
		System.out.println("jaenyeongProperties Name : " + jaenyeongProperties.getName());
		System.out.println("jaenyeongProperties proddb : " + jaenyeongProperties.getProddb());
		System.out.println("----------------------------------------------------------");
	}
}
