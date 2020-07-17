package com.jaenyeong.springboot_started;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @Configuration + @ComponentScan + @EnableAutoConfiguration 과 같음
// JPA 의존성으로 인해 실행 에러 (database setting 문제)
@SpringBootApplication

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		// @SpringBootApplication 주석처리
		// @Configuration, @ComponentScan 어노테이션 태깅시 실행하면 아래와 같은 에러 발생
		// Unable to start ServletWebServerApplicationContext due to missing ServletWebServerFactory bean.
		SpringApplication.run(Application.class, args);

		// 따라서 WebApplicationType 타입이 아닌 타입으로 실행
//		SpringApplication application = new SpringApplication(Application.class);
//		application.setWebApplicationType(WebApplicationType.NONE);
//		application.run(args);
	}
}
