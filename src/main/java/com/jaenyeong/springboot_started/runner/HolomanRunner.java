package com.jaenyeong.springboot_started.runner;

import com.jaenyeong.springboot_started.auto_configure.Holoman;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class HolomanRunner implements ApplicationRunner {

	@Autowired
	Holoman holoman;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Holoman print");
		System.out.println(holoman);
	}
}
