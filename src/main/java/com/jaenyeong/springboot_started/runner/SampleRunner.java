package com.jaenyeong.springboot_started.runner;

//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {
//public class SampleRunner implements CommandLineRunner {
//public class SampleRunner {

    // ApplicationRunner 구현시 메서드
	// VM options 무시
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("SampleRunner foo : " + args.containsOption("foo"));
		System.out.println("SampleRunner bar : " + args.containsOption("bar"));
	}

	// CommandLineRunner 구현시 메서드
	// VM options 무시
//	@Override
//	public void run(String... args) throws Exception {
//		Arrays.stream(args).forEach(System.out::println);
//	}
}
