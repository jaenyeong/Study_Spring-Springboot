package com.jaenyeong.springboot_started;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

// 실행시 아규먼트 확인
@Component
public class ArgumentCheck {

	// 생성자에 파라미터가 빈인 경우 스프링이 자동으로 주입
	public ArgumentCheck(ApplicationArguments arguments) {
		System.out.println("ArgumentCheck foo : " + arguments.containsOption("foo"));
		System.out.println("ArgumentCheck bar : " + arguments.containsOption("bar"));
	}
}
