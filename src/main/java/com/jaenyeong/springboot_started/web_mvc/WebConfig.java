package com.jaenyeong.springboot_started.web_mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 추가 설정 파일
@Configuration
// @EnableWebMvc 사용시 스프링 부트가 제공해주는 자동설정 내용을 사용 하지 않는 것
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/jn/**")
		.addResourceLocations("classpath:/jn/")
		.setCachePeriod(20);
	}
}
