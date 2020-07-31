package com.jaenyeong.springboot_started.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
@AutoConfigureDataMongo
public class HomeControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	@WithMockUser
	public void hello() throws Exception {
		mockMvc.perform(get("/security/hello")
				.accept(MediaType.TEXT_HTML))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("security-hello"));
	}

	@Test
	public void hello_without_user() throws Exception {
		mockMvc.perform(get("/security/hello")
				.accept(MediaType.TEXT_HTML))
				.andDo(print())
				.andExpect(status().is3xxRedirection());
	}

	@Test
	@WithMockUser
	public void my() throws Exception {
		mockMvc.perform(get("/security/my"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("security-my"));
	}
}
