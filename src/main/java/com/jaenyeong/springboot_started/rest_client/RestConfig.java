package com.jaenyeong.springboot_started.rest_client;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestConfig implements WebMvcConfigurer {

	// 커스터마이징
	// WebClientCustomizer가 아닌 빌더를 빈으로 등록 가능
	@Bean
	public WebClientCustomizer webClientCustomizer() {
		return webClientBuilder -> webClientBuilder.baseUrl("http://localhost:8888/rest");
	}

	@Bean
	public RestTemplateCustomizer restTemplateCustomizer() {
		return restTemplate -> restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	}
}
