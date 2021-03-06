package com.jaenyeong.springboot_started.web_mvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	// @WebMvcTest 어노테이션 사용시 해당 객체를 자동으로 빈으로 등록해줌
	@Autowired
	MockMvc mockMvc;

	@Test
	public void hello() throws Exception {
		mockMvc.perform(get("/user"))
				.andExpect(status().isOk())
				.andExpect(content().string("user"));
	}

	@Test
	public void createUserJson() throws Exception {
		String userJson = "{\"userName\" : \"jaenyeong\", \"age\": \"20\"}";
		mockMvc.perform(
				post("/users/create")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userName"
						, is(equalTo("jaenyeong"))))
				.andExpect(jsonPath("$.age"
						, is(equalTo(20))));
	}

	// ContentNegotiationViewResolver 테스트
	@Test
	public void createUserXML() throws Exception {
		String userJson = "{\"userName\" : \"jaenyeong\", \"age\": \"20\"}";
		mockMvc.perform(
				post("/users/create")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_XML)
						.content(userJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(xpath("/User/userName").string("jaenyeong"))
				.andExpect(xpath("/User/age").string("20"));
	}
}
