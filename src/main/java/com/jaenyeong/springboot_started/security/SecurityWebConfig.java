package com.jaenyeong.springboot_started.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityWebConfig implements WebMvcConfigurer {

	// 컨트롤러에서 별다른 처리 없이 뷰로 넘기기만 하는 경우
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/security/view/hello").setViewName("hello");
	}
}
