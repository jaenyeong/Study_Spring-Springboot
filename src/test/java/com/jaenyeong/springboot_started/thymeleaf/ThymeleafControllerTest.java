package com.jaenyeong.springboot_started.thymeleaf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(ThymeleafController.class)
public class ThymeleafControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void hello() throws Exception {
		mockMvc.perform(get("/thymeleaf/hello"))
				// 타임리프를 사용해서 콘솔에서 뷰가 렌더링하는 것을 확인
				// JSP 사용시 렌더링 결과 확인 어려움
				// 서블릿 엔진이 렌더링 함
				.andDo(print())
				.andExpect(status().isOk())
				// 뷰네임 확인 (hello)
				.andExpect(view().name("hello"))
				// 모델 내 데이터 확인 (name jaenyeong)
				.andExpect(model().attribute("name", is("jaenyeong")))
				// 뷰 안에 렌더링된 내용 확인
				.andExpect(content().string(containsString("Noah")))
				.andExpect(content().string(containsString("jaenyeong")))
				// xPath
				.andExpect(xpath("//h1").string("jaenyeong"));
	}
}
