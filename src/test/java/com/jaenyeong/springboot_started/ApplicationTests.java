package com.jaenyeong.springboot_started;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)

//@TestPropertySource(properties = "jaenyeong.name=jaenyeong2") // 2순위
//@TestPropertySource(locations = "classpath:/addJaenyeong.properties") // 2순위

// 팩토리 등 추가 설정 잆이 yaml 설정 파일을 사용하는 경우
@TestPropertySource(properties = {"spring.config.location = classpath:/addJaenyeong2.yaml"}) // 2순위

//@TestPropertySource(locations = "classpath:/addJaenyeong2.yaml") // 2순위

//@SpringBootTest(properties = "jaenyeong.name=jaenyeong3") // 3순위
@SpringBootTest // 3순위
public class ApplicationTests {

	@Autowired
	Environment environment;

	// 프로덕션과 테스트 코드의 application.properties(yaml) 파일이 다른 경우
	// 테스트만 실행시 테스트 경로의 application.properties(yaml) 파일로 덮어써지기 때문에 내용 유무나 값에 따라 실패할 수 있음
	@Test
	public void contextLoads() {
		assertThat(environment.getProperty("jaenyeong.name"))
				// 프로덕션 코드에 있는 설정 파일 - appication.properties(yaml) 참조
//				.isEqualTo("jaenyeong");

				// test/resources 경로에 있는 설정 파일 참조
//				.isEqualTo("testNoah");

				// @TestPropertySource(properties = "jaenyeong.name=jaenyeong2") 참조
//				.isEqualTo("jaenyeong2");
				// addJaenyeong.properties 파일 참조
//				.isEqualTo("testJaenyeongProperties");
				// addJaenyeong2.yaml 파일 참조
				.isEqualTo("testJaenyeongYaml");

				// @SpringBootTest(properties = "jaenyeong.name=jaenyeong3") 참조
//				.isEqualTo("jaenyeong3");

		assertThat(environment.getProperty("jaenyeong.age"))
				// 프로덕션 코드에 있는 설정 파일 참조
//				.isEqualTo(12345);

		        // test/resources 경로에 있는 설정 파일 참조
//				.isEqualTo(77777);

				// addJaenyeong2.yaml 설정 파일 사용
				.isEqualTo("11111");
	}
}
