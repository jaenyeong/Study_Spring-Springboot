package com.jaenyeong.springboot_started.runner;

//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;

import com.jaenyeong.springboot_started.properties.JaenyeongProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {
//public class SampleRunner implements CommandLineRunner {
//public class SampleRunner {

	// application.properties(yaml) 안에 데이터 참조 (우선 순위 15위)
	@Value("${jaenyeong.name}")
	private String name;
	@Value("${jaenyeong.age}")
	private int age;
	@Value("${jaenyeong.fullName}")
	private String fullName;

	// 소스로 작성한 프로퍼티 주입
	@Autowired
	private JaenyeongProperties jaenyeongProperties;

    // ApplicationRunner 구현시 메서드
	// VM options 무시
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("SampleRunner foo : " + args.containsOption("foo"));
		System.out.println("SampleRunner bar : " + args.containsOption("bar"));

		// application.properties 데이터 출력
		System.out.println("[SampleRunner1]----------------------------------------------------------");
		System.out.println("application properties name : " + name);
		System.out.println("application properties age : " + age);
		System.out.println("application properties fullName : " + fullName);
		System.out.println("[SampleRunner1]----------------------------------------------------------");

		System.out.println("[SampleRunner2]----------------------------------------------------------");
		System.out.println("JaenyeongProperties name : " + jaenyeongProperties.getName());
		System.out.println("JaenyeongProperties age : " + jaenyeongProperties.getAge());
		System.out.println("JaenyeongProperties fullName : " + jaenyeongProperties.getFullName());
		System.out.println("JaenyeongProperties sessionTimeout : " + jaenyeongProperties.getSessionTimeout());
		System.out.println("JaenyeongProperties nullParam : " + jaenyeongProperties.getNullParam());
		System.out.println("[SampleRunner2]----------------------------------------------------------");
	}

	// CommandLineRunner 구현시 메서드
	// VM options 무시
//	@Override
//	public void run(String... args) throws Exception {
//		Arrays.stream(args).forEach(System.out::println);
//	}
}
