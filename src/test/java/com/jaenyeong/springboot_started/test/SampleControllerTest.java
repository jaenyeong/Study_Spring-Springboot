package com.jaenyeong.springboot_started.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
// 서블릿 컨테이너를 mock up 해서 mocking된 디스패처 서블릿이 로딩
// 진짜 디스패처 서블릿에게 요청을 보내는 것을 흉내냄 (mocking된 디스패처 서블릿에게 요청)
// 이 때 반드시 MockMvc 클라이언트를 사용해야 함 이를 위해 @AutoConfigureMockMvc 어노테이션 태깅
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)

// 랜덤 포트 사용시 실제 내장 톰캣이 로딩
// RestTemplate, TestRestTemplate, WebClient, WebTestClient 등을 사용해야 함
// 랜덤 포트로 테스트시 기존 application.yaml 파일에 서버 설정 주석처리
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

//@AutoConfigureMockMvc

// JsonTest
//@JsonTest

// WebMvcTest
// 해당 어노테이션은 내부 객체조차 주입되지 않기 때문에 내부 객체 사용시 주입 필요
// @Service, @Repository와 같은 일반적인 빈은 등록되지 않음
@WebMvcTest(SampleController.class)
public class SampleControllerTest {
	private final String resultValue = "Hello Spring ";

	// 서버 사이드 테스트 시 사용
	@Autowired
	MockMvc mockMvc;

	// 클라이언트 사이드 테스트 시 사용
//	@Autowired
//	TestRestTemplate testRestTemplate;
//
	// 서비스를 목 객체로 주입 받음 (내부 서비스 객체도 목 객체)
	@MockBean
	SampleService mockSampleService;

	// 스프링5 웹 플럭스에 추가
	// 비동기적으로 실행됨
//	@Autowired
//	WebTestClient webTestClient;

	// JsonTest
//	@Autowired
//	JacksonTester<Domain> jacksonTester;

	// public으로 선언할 것
	@Rule
	public OutputCaptureRule outputCaptureRule = new OutputCaptureRule();

	@Test
	public void hello() throws Exception {
		// mockMVC or @WebMvcTest
//		mockMvc.perform(get("/hello"))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(content().string(resultValue + "jaenyeong"));

		// TestRestTemplate
//		// SampleService 객체를 목 객체로 받는 경우 에러
////		String result1 = testRestTemplate.getForObject("/hello", String.class);
////		assertThat(result1).isEqualTo(resultValue + "jaenyeong");
//
//		when(mockSampleService.getName()).thenReturn("noah");
//		String result2 = testRestTemplate.getForObject("/hello", String.class);
//		assertThat(result2).isEqualTo(resultValue + "noah");

		// WebTestClient
//		when(mockSampleService.getName()).thenReturn("noah");
//		webTestClient.get()
//				.uri("/hello")
//				.exchange()
//				.expectStatus().isOk()
//				.expectBody(String.class).isEqualTo(resultValue + "noah");

		// @Rule OutputCaptureRule, @WebMvcTest
		when(mockSampleService.getName()).thenReturn("noah");
		mockMvc.perform(get("/hello"))
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(resultValue + "noah"));

		assertThat(outputCaptureRule.toString())
				.contains("Sample Ctr System.out.println")
				.contains("ABC");
	}
}
