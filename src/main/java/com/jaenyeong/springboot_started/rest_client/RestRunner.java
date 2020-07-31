package com.jaenyeong.springboot_started.rest_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestRunner implements ApplicationRunner {

	// 스프링 MVC 모듈이 가지고 있기 때문에 사용 가능
	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@Autowired
	WebClient.Builder webBuilder;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		RestTemplate restTemplate = restTemplateBuilder.build();
		WebClient webClient = webBuilder.build();
		// 설정 파일에서 WebClientCustomizer 설정
//		WebClient webClient = webBuilder.baseUrl("http://localhost:8888/rest").build();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		// Blocking I/O - 동기
//		String helloResult = restTemplate.getForObject("http://localhost:8888/rest/hello", String.class);
//		System.out.println("hello result : " + helloResult);
//
//		String worldResult = restTemplate.getForObject("http://localhost:8888/rest/world", String.class);
//		System.out.println("world result : " + worldResult);

		// Non Blocking I/O - 비동기
		Mono<String> helloMono = webClient.get()
//				.uri("http://localhost:8888/rest/hello")
				.uri("/hello")
				.retrieve()
				.bodyToMono(String.class);
		streamMono(helloMono, stopWatch);

		Mono<String> worldMono = webClient.get()
//				.uri("http://localhost:8888/rest/world")
				.uri("/world")
				.retrieve()
				.bodyToMono(String.class);
		streamMono(worldMono, stopWatch);

		stopWatch.stop();
		System.out.println(stopWatch.prettyPrint());
	}

	private void streamMono(Mono<String> param, StopWatch stopWatch) {
		param.subscribe(s -> {
			System.out.println(s);

			if (stopWatch.isRunning()) {
				stopWatch.stop();
			}

			System.out.println(stopWatch.prettyPrint());
			stopWatch.start();
		});
	}
}
