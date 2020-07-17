package com.jaenyeong.springboot_started;

import com.jaenyeong.springboot_started.auto_configure.Holoman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class HolomanRunner implements ApplicationRunner {

	@Autowired
	Holoman holoman;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(holoman);
	}
}
